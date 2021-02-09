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

}
