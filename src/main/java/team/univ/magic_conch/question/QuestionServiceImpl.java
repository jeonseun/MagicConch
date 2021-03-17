package team.univ.magic_conch.question;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.univ.magic_conch.answer.AnswerRepository;
import team.univ.magic_conch.bundle.Bundle;
import team.univ.magic_conch.bundle.BundleRepository;
import team.univ.magic_conch.bundle.dto.BundleDropBoxDTO;
import team.univ.magic_conch.question.dto.*;
import team.univ.magic_conch.question.form.QuestionForm;
import team.univ.magic_conch.tag.TagRepository;
import team.univ.magic_conch.user.User;
import team.univ.magic_conch.user.UserRepository;
import team.univ.magic_conch.utils.page.PageResultDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private final UserRepository userRepository;

    @Override
    public List<BundleDropBoxDTO> question(String username){
        return bundleRepository.findAllByUserUsername(username)
                .stream().map(Bundle::entityToBundleDropBoxDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = false)
    public Long createQuestion(Question question){
        Question q = questionRepository.save(question);
        return q.getId();
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

    @Override
    public PageResultDTO getQuestionsByBundleId(Long bundleId, Pageable pageable) {
        Page<Question> result = questionRepository.findAllByBundleId(bundleId, pageable);
        Function<Question, QuestionInfoDTO> fn = Question::entityToQuestionInfoDTO;
        return new PageResultDTO(result, fn);
    }

    @Override
    public QuestionMainDTO questionMain() {

        QuestionMainDTO questionMainDTO = QuestionMainDTO.builder()
                .total(questionRepository.count())
                .todayTotal(questionRepository.countbyTodayDate(LocalDateTime.now().withHour(0).withMinute(0).withSecond(0), LocalDateTime.now()))
                .noSolvedTotal(questionRepository.countByStatus(QuestionStatus.ING))
                .solvedTotal(questionRepository.countByStatus(QuestionStatus.END))
                .questionList(questionRepository.findTop5ByStatusOrderByCreateTimeAsc(QuestionStatus.ING)
                                .stream()
                                .map(Question::entityToQuestionListDto)
                                .collect(Collectors.toList()))
                .build();

        return questionMainDTO;
    }

    @Override
    public List<Question> getMyLatestQuestion(User user, int count) {
        List<Question> findQuestions = questionRepository.findByUserOrderByCreateTimeDesc(user);
        List<Question> questions = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            questions.add(findQuestions.get(i));
        }
        return questions;
    }
}
