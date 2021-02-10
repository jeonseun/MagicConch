package team.univ.magic_conch.answer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import team.univ.magic_conch.answer.dto.AnswerDTO;
import team.univ.magic_conch.answer.dto.CreateAnswerDTO;

@Controller
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    /**
     * 답변 생성 요청
     * @param createAnswerDTO
     * @return 생성된 답변
     */
    @PostMapping("/answer")
    @ResponseBody
    public AnswerDTO answer(CreateAnswerDTO createAnswerDTO) {

        return  answerService.createAnswer(createAnswerDTO);

    }

}
