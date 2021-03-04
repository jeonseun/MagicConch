package team.univ.magic_conch.answer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import team.univ.magic_conch.answer.dto.AnswerDTO;
import team.univ.magic_conch.answer.dto.CreateAnswerDTO;
import team.univ.magic_conch.answer.dto.UpdateAnswerDTO;

import javax.validation.Valid;
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
    public AnswerDTO createAnswer(@Valid CreateAnswerDTO createAnswerDTO) {
        return answerService.createAnswer(createAnswerDTO);
    }

    /**
     * 답변 변경 요청
     * @param updateAnswerDTO
     * @return
     */
    @PutMapping("/answer")
    @ResponseBody
    public AnswerDTO updateAnswer(UpdateAnswerDTO updateAnswerDTO){

        return answerService.updateAnswer(updateAnswerDTO);

    }

    /**
     * 답변 삭제 요청
     * @param answerId
     */
    @DeleteMapping("/answer")
    @ResponseBody
    public String deleteAnswer(@RequestParam Long answerId){

        answerService.deleteAnswer(answerId);
        return "success";
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
