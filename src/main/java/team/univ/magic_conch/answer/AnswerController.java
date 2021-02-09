package team.univ.magic_conch.answer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import team.univ.magic_conch.answer.dto.CreateAnswerDTO;

@Controller
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    /**
     * 답변 생성 요청
     * @param createAnswerDTO
     * @return 답변 생성 여부
     */
    @PostMapping("/answer")
    @ResponseBody
    public String answer(CreateAnswerDTO createAnswerDTO) {

        Answer answer = answerService.answer(createAnswerDTO);

        if(answer != null){
            return "success";
        }else{
            return "fail";
        }
    }

}
