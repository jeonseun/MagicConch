package team.univ.magic_conch.question;

import team.univ.magic_conch.bundle.dto.BundleDropBoxDTO;
import team.univ.magic_conch.question.dto.QuestionDetailDTO;
import team.univ.magic_conch.question.dto.QuestionFollowDTO;
import team.univ.magic_conch.question.dto.QuestionListDTO;
import team.univ.magic_conch.utils.page.PageRequestDTO;
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
    public void questionForm(Question question);

    /**
     * 상세 질문 보기
     * @param questionNo
     * @return 질문 상세 정보
     */
    public QuestionDetailDTO questionDetail(Long questionNo);

    /**
     * 질문 다중 검색
     * @param title
     * @param username
     * @param tagName
     * @return 해당되는 파라미터에 대해 검색된 질문 목록
     */
    public PageResultDTO<QuestionListDTO, Question> questionAllByTitleOrUsernameOrTagName(String title, String username, String tagName, PageRequestDTO pageRequestDTO);

    /**
     * 해당 게시글 방문 - > 조회수 추가
     * @param questionId
     */
    public void plusViews(Long questionId);

    /**
     * 내가 팔로우 한 사람들 질문 목록, 최신순, 미해결 질문 중에서만 추출
     * @param name
     * @return 내가 팔로우 한 사람들 질문 목록
     */
    public PageResultDTO<QuestionFollowDTO, Question> questionFollow(String name, PageRequestDTO pageRequestDTO);

}
