package team.univ.magic_conch.question;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team.univ.magic_conch.utils.page.PageRequestDTO;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService{

    private final QuestionRepository questionRepository;

    /**
     * 질문하기(GET)
     * @return 해당 유저가 가지고 있는 bundle 목록
     */
    @Override
    public void question(){
        /* 번들 목록 리턴 구현 필요 */
    }

    /**
     * 질문하기(POST)
     * @param question
     */
    @Override
    public void createQuestion(Question question){
        questionRepository.save(question);
    }

    /**
     * 상세 질문 보기
     * @param questionNo
     * @return 질문 상세 정보
     */
    @Override
    public Optional<Question> questionDetail(int questionNo) {
        return Optional.ofNullable(questionRepository.findById(questionNo));
    }

    /**
     * 전체 질문 목록 보기
     * @param pageRequestDTO
     * @return
     */
    @Override
    public List<Question> questionAll(PageRequestDTO pageRequestDTO) {

    }

    /**
     * 유저 검색
     * @param username
     * @param pageRequestDTO
     * @return
     */
    @Override
    public List<Question> questionAllByUsername(String username, PageRequestDTO pageRequestDTO) {

    }

    /**
     * 질문 제목 검색
     * @param questionname
     * @param pageRequestDTO
     * @return
     */
    @Override
    public List<Question> questionAllByQuestionName(String questionname, PageRequestDTO pageRequestDTO) {

    }
}
