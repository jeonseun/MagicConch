package team.univ.magic_conch.answer;

import team.univ.magic_conch.answer.dto.AnswerDTO;
import team.univ.magic_conch.answer.dto.CreateAnswerDTO;
import team.univ.magic_conch.answer.dto.UpdateAnswerDTO;

import java.util.List;

public interface AnswerService {

    /**
     * 신규 답변 생성
     * 특정 질문에 새로운 답변을 생성하고 저장한다.
     *
     * @param createAnswerDTO
     * @return 생성된 답변
     */
    public AnswerDTO createAnswer(CreateAnswerDTO createAnswerDTO);

    /**
     * 답변 수정
     * @param updateAnswerDTO
     */
    public AnswerDTO updateAnswer(UpdateAnswerDTO updateAnswerDTO);

    /**
     * 답변 삭제
     * @param answerId
     */
    public void deleteAnswer(Long answerId);

    /**
     * 답변 채택
     * @param answerId
     */
    public void adoptAnswer(Long questionId, Long answerId);

    /**
     * 게시글에 질문 보여주기
     * @param questionId
     * @return 게시글에 달린 질문 목록
     */
    public List<AnswerDTO> answer(Long questionId);

    /**
     * 해당 질문에 달린 답변 개수를 반환
     * @param questionId
     * @return answer count : long
     */
    long getCountByQuestionID(Long questionId);


}
