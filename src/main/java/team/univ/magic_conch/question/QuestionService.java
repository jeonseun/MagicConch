package team.univ.magic_conch.question;

import org.springframework.data.domain.Pageable;
import team.univ.magic_conch.bundle.Bundle;
import team.univ.magic_conch.bundle.dto.BundleDropBoxDTO;
import team.univ.magic_conch.question.dto.QuestionDetailDTO;
import team.univ.magic_conch.question.dto.QuestionListDTO;
import team.univ.magic_conch.question.dto.QuestionSearchDTO;
import team.univ.magic_conch.question.form.QuestionForm;
import team.univ.magic_conch.utils.page.PageResultDTO;

import java.util.List;

public interface QuestionService {

    /**
     * 질문하기(GET)
     * @return 해당 유저가 가지고 있는 bundle 목록
     */
    public List<BundleDropBoxDTO> question(String username);
    
    /**
     * 질문하기(POST)
     * @param question
     */
    public void createQuestion(Question question);

    /**
     * 질문수정(PUT)
     * @param questionForm
     */
    public void updateQuestion(QuestionForm questionForm);

    /**
     * 질문삭제(DELETE)
     * @param question
     */
    public void deleteQuestion(Question question);

    /**
     * 상세 질문 보기
     * @param questionNo
     * @return 질문 상세 정보
     */
    public QuestionDetailDTO questionDetail(Long questionNo);

    /**
     * 질문 다중 검색
     * @param questionSearchDTO
     * @return 해당되는 파라미터에 대해 검색된 질문 목록
     */
    public PageResultDTO<QuestionListDTO, Question> questionAllByTitleOrUsernameOrTagName(QuestionSearchDTO questionSearchDTO);

    /**
     * 해당 게시글 방문 - > 조회수 추가
     * @param questionId
     */
    public void plusViews(Long questionId);

    /**
     * 내가 팔로우 한 사람들 질문 목록, 최신순, 미해결 질문 중에서만 추출
     * @param myname
     * @param questionSearchDTO
     * @return 내가 팔로우 한 사람들 질문 목록
     */
    public PageResultDTO<QuestionListDTO, Question> questionFollow(String myname, QuestionSearchDTO questionSearchDTO);

    /**
     * 해당 번들에 속해있는 질문 개수 조회
     * @param bundleId
     * @return question count : long
     */
    long getQuestionCount(Long bundleId);

    /**
     * 해당 번들에 속해있는 질문 페이징 조회
     * @param bundle   질문이 속한 번들
     * @param pageable 현재 페이지, 페이지당 사이즈, 정렬 기준
     * @return 해당 번들에 속한 질문 페이징 DTO
     */
    PageResultDTO getQuestionsByBundleId(Bundle bundle, Pageable pageable);
}
