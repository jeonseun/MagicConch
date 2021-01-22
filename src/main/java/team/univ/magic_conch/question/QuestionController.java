package team.univ.magic_conch.question;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import team.univ.magic_conch.bundle.BundleService;
import team.univ.magic_conch.config.auth.PrincipalDetails;
import team.univ.magic_conch.question.form.QuestionForm;
import team.univ.magic_conch.tag.TagService;
import team.univ.magic_conch.utils.page.PageRequestDTO;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

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
        /* 번들이 Default 인지를 구분하는 로직 구현 필요 */
        Question question = Question.builder()
                .title(questionForm.getTitle())
                .content(questionForm.getContent())
                .createTime(LocalDateTime.now())
                .lastModifyTime(LocalDateTime.now())
                .user(principalDetails.getUser())
                .bundle(bundleService.findById(questionForm.getBundleId()).orElse(null))
                .tag(tagService.findByName(questionForm.getTagName()))
                .build();
        questionService.questionForm(question);
        return "redirect:/question/" + question.getId();
    }

    @GetMapping("/question/{questionNo}")
    public String questionDetail(Model model, @PathVariable Optional<Integer> questionNo){
        int num = questionNo.isPresent() ? questionNo.get() : 0;
        return "/question/questionDetail";
    }

    @GetMapping("question/list")
    public String questionList(Model model,
                               @RequestParam(value = "page") Optional<Integer> pageNo,
                               @RequestParam(value = "user") Optional<String> userName,
                               @RequestParam(value = "title") Optional<String> title){

        PageRequestDTO pageRequestDTO = new PageRequestDTO(pageNo.orElse(1));

        if(userName.isPresent()){
            model.addAttribute("list", questionService.questionAllByUsername(userName.get(), pageRequestDTO));
        } else if(title.isPresent()){
            model.addAttribute("list", questionService.questionAllByTitle(title.get(), pageRequestDTO));
        } else{
            model.addAttribute("list", questionService.questionAll(pageRequestDTO));
        }

        return "/question/questionList";
    }

}
