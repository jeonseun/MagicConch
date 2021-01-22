package team.univ.magic_conch.question;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.univ.magic_conch.bundle.Bundle;
import team.univ.magic_conch.bundle.BundleRepository;
import team.univ.magic_conch.bundle.dto.BundleDropBoxDTO;
import team.univ.magic_conch.question.dto.QuestionDetailDTO;
import team.univ.magic_conch.question.dto.QuestionListDTO;
import team.univ.magic_conch.user.User;
import team.univ.magic_conch.utils.page.PageRequestDTO;
import team.univ.magic_conch.utils.page.PageResultDTO;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionServiceImpl implements QuestionService{

    private final QuestionRepository questionRepository;
    private final BundleRepository bundleRepository;

    /**
     * 질문하기(GET)
     * @return 해당 유저가 가지고 있는 bundle 목록
     */
    @Override
    public List<BundleDropBoxDTO> question(String name){
        return bundleRepository.findAllByUserUsername(name)
                .stream().map(Bundle::entityToBundleDropBoxDTO)
                .collect(Collectors.toList());
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
        return questionRepository.findById(questionNo).get().entityToQuestionDetailDto();
    }

    /**
     * 질문 다중 검색
     * @param title
     * @param username
     * @param tagName
     * @return 해당되는 파라미터에 대해 검색된 질문 목록
     */
    @Override
    public PageResultDTO<QuestionListDTO, Question> questionAllByTitleOrUsernameOrTagName(String title, String username, String tagName, PageRequestDTO pageRequestDTO) {
        Page<Question> result = questionRepository.findAllByTitleOrUsernameOrTagName(title, username, tagName, pageRequestDTO.getPageable());
        Function<Question, QuestionListDTO> fn = (Question::entityToQuestionListDto);
        return new PageResultDTO<>(result, fn);
    }

    /**
     * 해당 게시글 방문 - > 조회수 추가
     * @param questionId
     */
    @Override
    @Transactional(readOnly = false)
    public void plusViews(Long questionId){
        Optional<Question> question = questionRepository.findById(questionId);
        question.orElse(null).changeView();
    }

}
