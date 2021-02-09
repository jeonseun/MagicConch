package team.univ.magic_conch.answer;

public interface AnswerService {

    /**
     * 해당 질문에 달린 답변 개수를 반환
     * @param questionId
     * @return answer count : long
     */
    long getCountByQuestionID(Long questionId);
}
