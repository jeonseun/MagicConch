package team.univ.magic_conch;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import team.univ.magic_conch.answer.AnswerService;
import team.univ.magic_conch.auth.PrincipalDetails;
import team.univ.magic_conch.follow.FollowService;
import team.univ.magic_conch.question.QuestionService;

import java.util.List;
import team.univ.magic_conch.bundle.Bundle;
import team.univ.magic_conch.bundle.BundleService;
import team.univ.magic_conch.bundle.dto.BundleInfoDTO;
import team.univ.magic_conch.question.Question;
import team.univ.magic_conch.question.dto.QuestionInfoDTO;
import team.univ.magic_conch.team.TeamService;

import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final QuestionService questionService;
    private final FollowService followService;
    private final AnswerService answerService;
    private final TeamService teamService;
    private final BundleService bundleService;

    @GetMapping("/")
    public String main(@AuthenticationPrincipal PrincipalDetails principalDetails,
                       Model model){

        questionService.questionMain();

        if(principalDetails == null){
            model.addAttribute("question", questionService.questionMain());
            model.addAttribute("follow", followService.findBestFollower());
            model.addAttribute("answer", answerService.findBestAdopted());
            return "bindex.html";
        }else{
            List<Question> questions = questionService.getMyLatestQuestion(principalDetails.getUser(), 10);
            List<QuestionInfoDTO> questionDTO = questions.stream().map(Question::entityToQuestionInfoDTO).collect(Collectors.toList());
            model.addAttribute("questions", questionDTO);

            List<Bundle> myBundles = bundleService.getMyBundle(principalDetails.getUsername());
            List<BundleInfoDTO> bundleDTO = myBundles.stream().map(Bundle::entityToInfoDTO).collect(Collectors.toList());
            model.addAttribute("bundles", bundleDTO);

            return "aindex.html";
        }

    }
}
