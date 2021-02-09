package team.univ.magic_conch.like;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team.univ.magic_conch.auth.PrincipalDetails;

@Controller
@RequiredArgsConstructor
public class QuestionLikeController {

    private final QuestionLikeService questionLikeService;

    @PostMapping("/questionLike")
    @ResponseBody
    public String createQuestion(@RequestParam Long questionId,
                                 @AuthenticationPrincipal PrincipalDetails principalDetails){
        QuestionLike questionLike = questionLikeService.createQuestionLike(principalDetails.getUsername(), questionId);
        System.out.println("생성");
        if(questionLike != null){
            return "success";
        }else{
            return "fail";
        }
    }

    @DeleteMapping("/questionLike")
    @ResponseBody
    public String deleteQuestion(@RequestParam Long questionId,
                                 @AuthenticationPrincipal PrincipalDetails principalDetails){
        QuestionLike questionLike = questionLikeService.deleteQuestionLike(principalDetails.getUsername(), questionId);
        System.out.println("제거");
        if(questionLike != null){
            return "success";
        }else{
            return "fail";
        }
    }

}