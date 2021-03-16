package team.univ.magic_conch;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/")
    public String main(@AuthenticationPrincipal PrincipalDetails principalDetails,
                       Model model){

        if(principalDetails == null){
            model.addAttribute("question", questionService.questionMain());
            model.addAttribute("follow", followService.findBestFollower());
            return "bindex.html";
        }else{
            return "aindex.html";
        }

    }
}
