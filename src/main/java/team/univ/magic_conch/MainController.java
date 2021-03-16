package team.univ.magic_conch;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import team.univ.magic_conch.answer.AnswerService;
import team.univ.magic_conch.auth.PrincipalDetails;
import team.univ.magic_conch.follow.FollowService;
import team.univ.magic_conch.follow.dto.BestFollowerDTO;
import team.univ.magic_conch.question.QuestionService;
import team.univ.magic_conch.question.dto.QuestionMainDTO;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final QuestionService questionService;
    private final FollowService followService;
    private final AnswerService answerService;

    @GetMapping("/")
    public String main(@AuthenticationPrincipal PrincipalDetails principalDetails,
                       Model model){

        if(principalDetails == null){
            model.addAttribute("question", questionService.questionMain());
            model.addAttribute("follow", followService.findBestFollower());
            model.addAttribute("answer", answerService.findBestAdopted());
            return "bindex.html";
        }else{
            return "aindex.html";
        }

    }
}
