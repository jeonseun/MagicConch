package team.univ.magic_conch.question;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import team.univ.magic_conch.answer.AnswerService;
import team.univ.magic_conch.answer.dto.AnswerDTO;
import team.univ.magic_conch.bundle.BundleService;
import team.univ.magic_conch.auth.PrincipalDetails;
import team.univ.magic_conch.like.QuestionLikeService;
import team.univ.magic_conch.question.dto.QuestionDetailDTO;
import team.univ.magic_conch.question.dto.QuestionListDTO;
import team.univ.magic_conch.question.dto.QuestionSearchDTO;
import team.univ.magic_conch.question.form.QuestionForm;
import team.univ.magic_conch.tag.Tag;
import team.univ.magic_conch.tag.TagService;
import team.univ.magic_conch.utils.page.PageRequestDTO;
import team.univ.magic_conch.utils.page.PageResultDTO;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionRepository questionRepository;
    private final AnswerService answerService;
    private final TagService tagService;
    private final BundleService bundleService;
    private final QuestionLikeService questionLikeService;

    /**
     * 질문 작성 페이지
     * @param model
     * @param principalDetails
     * @return 질문 페이지 view
     */
    @GetMapping("/question")
    public String question(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
        model.addAttribute("bundleList", questionService.question(principalDetails.getUsername()));
        return "/question/question";
    }

    /**
     * 질문 수정 페이지
     * @param model
     * @param questionNo
     * @param principalDetails
     * @return 질문 수정 페이지 view
     */
    @GetMapping("/question/{questionNo}/modify")
    public String questionModify(Model model,
                                 @PathVariable Long questionNo,
                                 @AuthenticationPrincipal PrincipalDetails principalDetails){
        model.addAttribute("questionInfo", questionService.questionDetail(questionNo));
        model.addAttribute("bundleList", questionService.question(principalDetails.getUsername()));
        return "/question/questionModify";
    }

    /**
     * 질문 작성 요청
     * @param questionForm
     * @param principalDetails
     * @return 작성한 질문 상세 페이지 view
     */
    @PostMapping("/question")
    public String createQuestion(@ModelAttribute QuestionForm questionForm,
                                 @AuthenticationPrincipal PrincipalDetails principalDetails){
        Question question = Question.builder()
                .title(questionForm.getTitle())
                .content(questionForm.getContent())
                .user(principalDetails.getUser())
                .bundle(bundleService.findById(questionForm.getBundleId()).orElse(null))
                .tag(tagService.findByName(questionForm.getTagName()))
                .build();
        questionService.createQuestion(question);
        return "redirect:/question/" + question.getId();
    }

    /**
     * 질문 수정 요청
     * @param questionForm
     * @return
     */
    @PutMapping("/question")
    public String updateQuestion(@ModelAttribute QuestionForm questionForm){
        questionService.updateQuestion(questionForm);
        return "redirect:/question/" + questionForm.getQuestionId();
    }

    /**
     * 질문 삭제 요청
     * @param questionNo
     * @return 성공 여부
     */
    @DeleteMapping("/question/{questionNo}")
    @ResponseBody
    public String deleteQuestion(@PathVariable Long questionNo){
        Optional<Question> question = questionRepository.findById(questionNo);
        if(question.isPresent()){
            questionService.deleteQuestion(question.get());
            return "success";
        }else{
            return "fail";
        }
    }

    /**
     * 질문 상세 페이지
     * @param req
     * @param res
     * @param model
     * @param questionNo
     * @param principalDetails
     * @return 상세 페이지 view
     */
    @GetMapping("/question/{questionNo}")
    public String questionDetail(HttpServletRequest req, HttpServletResponse res, Model model,
                                 @PathVariable Long questionNo,
                                 @AuthenticationPrincipal PrincipalDetails principalDetails){
        QuestionDetailDTO questionDetail = questionService.questionDetail(questionNo);

        /* 조회수 중복방지 */
        Cookie[] cookies = req.getCookies();
        if(cookies != null && principalDetails.getUsername() != questionDetail.getUsername()){
            for (int i = 0; i < cookies.length; i++) {
                if(cookies[i].getName().equals("viewCookie" + questionNo)){
                    break;
                }
                if(i == cookies.length - 1) {
                    questionService.plusViews(questionNo);
                    Cookie cookie = new Cookie("viewCookie" + questionNo, String.valueOf(questionNo));
                    cookie.setMaxAge(60 * 60 * 24);
                    cookie.setPath("/");
                    res.addCookie(cookie);
                    break;
                }
            }
        }

        model.addAttribute("question", questionDetail);
        model.addAttribute("answer", answerService.answer(questionNo));
        model.addAttribute("questionLike", questionLikeService.isQuestionLike(principalDetails.getUsername(), questionNo));
        return "/question/questionDetail";
    }

    /**
     * 질문 목록 페이지
     * @param model
     * @param pageNo
     * @return 질문 목록 페이지 view
     */
    @GetMapping("question/list")
    public String questionList(Model model,
                               @RequestParam(value = "page", defaultValue = "1") Integer pageNo){

        PageRequestDTO pageRequestDTO = new PageRequestDTO(pageNo);

        model.addAttribute("questionList", questionService.questionAllByTitleOrUsernameOrTagName(new QuestionSearchDTO(1, null, null, null)));
        model.addAttribute("tagList", tagService.findAll().stream().map(Tag::entityToTagDto).collect(Collectors.toList()));

        return "/question/questionList";
    }

    /**
     * 질문 목록 페이지
     * @param questionSearchDTO
     * @return 질문 목록 + 페이징 정보만
     */
    @GetMapping("api/v1/question/list")
    @ResponseBody
    public PageResultDTO<QuestionListDTO, Question> questionList(QuestionSearchDTO questionSearchDTO){

        return questionService.questionAllByTitleOrUsernameOrTagName(questionSearchDTO);
    }

    /**
     * 팔로우 질문 목록 페이지
     * @param model
     * @param pageNo
     * @param principalDetails
     * @return 팔로우 질문 목록 페이지 view
     */
    @GetMapping("question/follow/list")
    public String questionFollowList(Model model,
                                     @RequestParam(value = "page", defaultValue = "1") Integer pageNo,
                                     @AuthenticationPrincipal PrincipalDetails principalDetails){

        String myname = principalDetails == null ? "" : principalDetails.getUsername();

        model.addAttribute("questionList", questionService.questionFollow(myname, new QuestionSearchDTO(1, null, null, null)));
        model.addAttribute("tagList", tagService.findAll().stream().map(Tag::entityToTagDto).collect(Collectors.toList()));

        return "/question/questionFollowList";
    }

    /**
     * 팔로우 질문 목록 페이지
     * @param questionSearchDTO
     * @param principalDetails
     * @return 팔로우 질문 목록 + 페이징 정보만
     */
    @GetMapping("api/v1/question/follow/list")
    @ResponseBody
    public PageResultDTO<QuestionListDTO, Question> questionFollowList(QuestionSearchDTO questionSearchDTO,
                                                                       @AuthenticationPrincipal PrincipalDetails principalDetails){

        String myname = principalDetails == null ? "" : principalDetails.getUsername();

        return questionService.questionFollow(myname, questionSearchDTO);

    }

    /**
     * 메인 화면
     * @param pageNo
     * @param principalDetails
     * @return 팔로우 질문 목록
     */
    @GetMapping("api/v1/index/question/follow/list")
    @ResponseBody
    public PageResultDTO<QuestionListDTO, Question> questionFollowList(@RequestParam(value = "page", defaultValue = "1") Integer pageNo,
                                                                       @AuthenticationPrincipal PrincipalDetails principalDetails){

        String myname = principalDetails == null ? "" : principalDetails.getUsername();

        return questionService.questionFollow(myname, new QuestionSearchDTO(1, null, null, null));
    }

    @GetMapping("/question/test")
    @ResponseBody
    public String getQuestionByBundleId(HttpSession httpSession, Pageable pageable) {
        Long bundleId = (Long) httpSession.getAttribute("bundleId");
        System.out.println(pageable);

        return "test";
    }
}
