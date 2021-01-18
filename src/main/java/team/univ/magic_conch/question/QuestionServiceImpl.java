package team.univ.magic_conch.question;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.univ.magic_conch.question.dto.QuestionDetailDTO;
import team.univ.magic_conch.question.dto.QuestionListDTO;
import team.univ.magic_conch.utils.page.PageRequestDTO;
import team.univ.magic_conch.utils.page.PageResultDTO;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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
    @Transactional(readOnly = false)
    public void questionForm(Question question){
        questionRepository.save(question);
    }

    /**
     * 상세 질문 보기
     * @param questionNo
     * @return 질문 상세 정보
     */
    @Override
    public QuestionDetailDTO questionDetail(Long questionNo) {
        return this.entityToQuestionDetailDto(questionRepository.findById(questionNo).get());
    }

    /**
     * 전체 질문 목록 보기
     * @param pageRequestDTO
     * @return 전체 질문 목록
     */
    @Override
    public PageResultDTO<QuestionListDTO, Question> questionAll(PageRequestDTO pageRequestDTO) {
        Page<Question> result = questionRepository.findAll(pageRequestDTO.getPageable(Sort.by("createTime").descending()));
        Function<Question, QuestionListDTO> fn = (this::entityToQuestionListDto);
        return new PageResultDTO<>(result, fn);
    }

    /**
     * 질문 제목으로 검색
     * @param title
     * @param pageRequestDTO
     * @return 질문 제목으로 검색한 질문 목록
     */
    @Override
    public PageResultDTO<QuestionListDTO, Question> questionAllByTitle(String title, PageRequestDTO pageRequestDTO) {
        Page<Question> result = questionRepository.findAllByTitle(title, pageRequestDTO.getPageable(Sort.by("createTime").descending()));
        Function<Question, QuestionListDTO> fn = (this::entityToQuestionListDto);
        return new PageResultDTO<>(result, fn);
    }
    
    /**
     * 유저 이름으로 검색
     * @param username
     * @param pageRequestDTO
     * @return 유저 이름으로 검색한 질문 목록
     */
    @Override
    public PageResultDTO<QuestionListDTO, Question> questionAllByUsername(String username, PageRequestDTO pageRequestDTO) {
        Page<Question> result = questionRepository.findAllByUsername(username, pageRequestDTO.getPageable(Sort.by("createTime").descending()));
        Function<Question, QuestionListDTO> fn = (this::entityToQuestionListDto);
        return new PageResultDTO<>(result, fn);
    }

}
