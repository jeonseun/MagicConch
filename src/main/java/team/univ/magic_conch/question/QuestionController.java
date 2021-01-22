package team.univ.magic_conch.question;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;
import team.univ.magic_conch.bundle.BundleSerivce;
import team.univ.magic_conch.config.auth.PrincipalDetails;
import team.univ.magic_conch.question.form.QuestionForm;
import team.univ.magic_conch.tag.Tag;
import team.univ.magic_conch.tag.TagService;
import team.univ.magic_conch.utils.page.PageRequestDTO;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final TagService tagService;
    private final BundleSerivce bundleSerivce;

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
                .bundle(bundleSerivce.findById(questionForm.getBundleId()).orElse(null))
                .tag(tagService.findByName(questionForm.getTagName()))
                .build();
        questionService.questionForm(question);
        return "redirect:/question/" + question.getId();
    }

    @GetMapping("/question/{questionNo}")
    public String questionDetail(Model model, @PathVariable Long questionNo){
        model.addAttribute("question", questionService.questionDetail(questionNo));
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

}
