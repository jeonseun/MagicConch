package team.univ.magic_conch.question;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import team.univ.magic_conch.bundle.BundleService;
import team.univ.magic_conch.auth.PrincipalDetails;
import team.univ.magic_conch.question.dto.QuestionDetailDTO;
import team.univ.magic_conch.question.dto.QuestionListDTO;
import team.univ.magic_conch.question.form.QuestionForm;
import team.univ.magic_conch.tag.Tag;
import team.univ.magic_conch.tag.TagService;
import team.univ.magic_conch.utils.page.PageRequestDTO;
import team.univ.magic_conch.utils.page.PageResultDTO;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final TagService tagService;
    private final BundleService bundleService;

    @GetMapping("/question")
    public String question(Model model, Principal principal){
        model.addAttribute("bundleList", questionService.question(principal.getName()));
        return "/question/question";
    }

    @PostMapping("/question")
    public String questionForm(@ModelAttribute QuestionForm questionForm, @AuthenticationPrincipal PrincipalDetails principalDetails){
        Question question = Question.builder()
                .title(questionForm.getTitle())
                .content(questionForm.getContent())
                .createTime(LocalDateTime.now())
                .lastModifyTime(LocalDateTime.now().withNano(0))
                .user(principalDetails.getUser())
                .bundle(bundleService.findById(questionForm.getBundleId()).orElse(null))
                .tag(tagService.findByName(questionForm.getTagName()))
                .build();
        questionService.questionForm(question);
        return "redirect:/question/" + question.getId();
    }

    @GetMapping("/question/{questionNo}")
    public String questionDetail(HttpServletRequest req, HttpServletResponse res, Model model, @PathVariable Long questionNo, @AuthenticationPrincipal PrincipalDetails principalDetails){
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
        return "/question/questionDetail";
    }

    @GetMapping("question/list")
    public String questionList(Model model,
                               @RequestParam(value = "page", defaultValue = "1") Integer pageNo,
                               @RequestParam(value = "user", required = false) String userName,
                               @RequestParam(value = "title", required = false) String title,
                               @RequestParam(value = "tag", required = false) String tagName){

        PageRequestDTO pageRequestDTO = new PageRequestDTO(pageNo);

        model.addAttribute("questionList", questionService.questionAllByTitleOrUsernameOrTagName(title, userName, tagName, pageRequestDTO));
        model.addAttribute("tagList", tagService.findAll().stream().map(Tag::entityToTagDto).collect(Collectors.toList()));

        return "/question/questionList";
    }

    @GetMapping("question/api/v1/list")
    @ResponseBody
    public PageResultDTO<QuestionListDTO, Question> questionListApi(Model model,
                                                                    @RequestParam(value = "page", defaultValue = "1") Integer pageNo,
                                                                    @RequestParam(value = "user", required = false) String userName,
                                                                    @RequestParam(value = "title", required = false) String title,
                                                                    @RequestParam(value = "tag", required = false) String tagName){

        PageRequestDTO pageRequestDTO = new PageRequestDTO(pageNo);

        return questionService.questionAllByTitleOrUsernameOrTagName(title, userName, tagName, pageRequestDTO);


    }
}
