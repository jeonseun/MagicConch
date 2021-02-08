package team.univ.magic_conch.question;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.univ.magic_conch.bundle.Bundle;
import team.univ.magic_conch.bundle.BundleRepository;
import team.univ.magic_conch.bundle.dto.BundleDropBoxDTO;
import team.univ.magic_conch.question.dto.QuestionDetailDTO;
import team.univ.magic_conch.question.dto.QuestionListDTO;
import team.univ.magic_conch.question.dto.QuestionSearchDTO;
import team.univ.magic_conch.question.form.QuestionForm;
import team.univ.magic_conch.tag.TagRepository;
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
    private final TagRepository tagRepository;

    @Override
    public List<BundleDropBoxDTO> question(String username){
        return bundleRepository.findAllByUserUsername(username)
                .stream().map(Bundle::entityToBundleDropBoxDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = false)
    public void createQuestion(Question question){
        questionRepository.save(question);
    }

    @Override
    @Transactional(readOnly = false)
    public void updateQuestion(QuestionForm questionForm) {
        Question question = questionRepository.findById(questionForm.getQuestionId()).orElse(null);
        question.changeTitle(questionForm.getTitle());
        question.changeContent(questionForm.getContent());
        question.changeTag(tagRepository.findByName(questionForm.getTagName()));
        question.changeBundle(bundleRepository.findById(questionForm.getBundleId()).orElse(null));
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteQuestion(Question question) {
        questionRepository.delete(question);
    }

    @Override
    public QuestionDetailDTO questionDetail(Long questionNo) {
        return questionRepository.findById(questionNo).get().entityToQuestionDetailDto();
    }

    @Override
    public PageResultDTO<QuestionListDTO, Question> questionAllByTitleOrUsernameOrTagName(QuestionSearchDTO questionSearchDTO) {
        Page<Question> result = questionRepository.findAllByTitleOrUsernameOrTagName(questionSearchDTO);
        Function<Question, QuestionListDTO> fn = (Question::entityToQuestionListDto);
        return new PageResultDTO<>(result, fn);
    }

    @Override
    @Transactional(readOnly = false)
    public void plusViews(Long questionId){
        Optional<Question> question = questionRepository.findById(questionId);
        question.orElse(null).changeView();
    }

    @Override
    @Transactional(readOnly = false)
    public PageResultDTO<QuestionListDTO, Question> questionFollow(String myname, QuestionSearchDTO questionSearchDTO) {
        Page<Question> result = questionRepository.findAllByFollowUsername(myname, questionSearchDTO);
        Function<Question, QuestionListDTO> fn = Question::entityToQuestionListDto;
        return new PageResultDTO<>(result, fn);
    }

}
