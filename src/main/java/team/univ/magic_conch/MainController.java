package team.univ.magic_conch;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import team.univ.magic_conch.auth.PrincipalDetails;
import team.univ.magic_conch.question.QuestionService;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final QuestionService questionService;

    @GetMapping("/")
    public String main(@AuthenticationPrincipal PrincipalDetails principalDetails){

        questionService.questionMain();

        if(principalDetails == null){
            return "bindex.html";
        }else{
            return "aindex.html";
        }

    }
}
