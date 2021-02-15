package team.univ.magic_conch.answer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import team.univ.magic_conch.answer.dto.AnswerDTO;
import team.univ.magic_conch.answer.dto.CreateAnswerDTO;

import java.util.List;

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
    public AnswerDTO createAnswer(CreateAnswerDTO createAnswerDTO) {

        return  answerService.createAnswer(createAnswerDTO);

    }

    /**
     * 해당 질문에 대한 답변 전달
     * @param questionId
     * @return 해당 질문에 대한 답변 목록
     */
    @GetMapping("/answer/info")
    @ResponseBody
    public List<AnswerDTO> answer(@RequestParam(value = "question") Long questionId){

        return answerService.answer(questionId);

    }

}
