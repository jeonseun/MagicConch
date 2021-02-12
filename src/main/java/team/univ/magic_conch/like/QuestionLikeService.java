package team.univ.magic_conch.like;

public interface QuestionLikeService {

    /**
     * 질문 좋아요
     * @param username
     * @param questionId
     * @return 좋아요
     */
    public QuestionLike createQuestionLike(String username, Long questionId);

    /**
     * 질문 좋아요 취소
     * @param username
     * @param questionId
     * @return
     */
    public QuestionLike deleteQuestionLike(String username, Long questionId);

    /**
     * 유저가 해당 질문에 좋아요 누른 여부 확인
     * @param username
     * @param questionId
     * @return 좋아요를 안누름 true, 좋아요를 눌렀음 false
     */
    public Boolean isQuestionLike(String username, Long questionId);

}
