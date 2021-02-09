package team.univ.magic_conch.answer;

import team.univ.magic_conch.answer.dto.CreateAnswerDTO;

public interface AnswerService {

    /**
     * 신규 답변 생성
     * 특정 질문에 새로운 답변을 생성하고 저장한다.
     *
     * @param createAnswerDTO
     * @return 생성된 답변
     */
    public Answer answer(CreateAnswerDTO createAnswerDTO);

}
