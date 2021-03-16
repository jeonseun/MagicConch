package team.univ.magic_conch.answer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.univ.magic_conch.answer.dto.AnswerDTO;
import team.univ.magic_conch.answer.dto.BestAdoptedDTO;
import team.univ.magic_conch.answer.dto.CreateAnswerDTO;
import team.univ.magic_conch.answer.dto.UpdateAnswerDTO;
import team.univ.magic_conch.question.Question;
import team.univ.magic_conch.question.QuestionRepository;
import team.univ.magic_conch.question.QuestionStatus;
import team.univ.magic_conch.user.User;
import team.univ.magic_conch.user.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = false)
    public AnswerDTO createAnswer(CreateAnswerDTO createAnswerDTO) {

        Optional<User> user = userRepository.findByUsername(createAnswerDTO.getUsername());
        Optional<Question> question = questionRepository.findById(createAnswerDTO.getQuestionId());
        Answer answer = null;

        if(user.isPresent() && question.isPresent()){
            answer = Answer.builder()
                    .content(createAnswerDTO.getContent())
                    .question(question.get())
                    .user(user.get())
                    .build();
            answerRepository.save(answer);
        }

        return answerRepository.findById(answer.getId()).get().entityToAnswerDTO();
    }

    @Override
    @Transactional(readOnly = false)
    public AnswerDTO updateAnswer(UpdateAnswerDTO updateAnswerDTO) {

        Optional<Answer> answer = answerRepository.findById(updateAnswerDTO.getAnswerId());
        if(answer.isPresent()){
            answer.get().changeContent(updateAnswerDTO.getContent());
            answer.get().refreshLastModifyTime();
        }

        return answer.get().entityToAnswerDTO();

    }

    @Override
    @Transactional(readOnly = false)
    public void deleteAnswer(Long answerId) {

        Optional<Answer> answer = answerRepository.findById(answerId);
        answer.ifPresent(answerRepository::delete);

    }

    @Override
    @Transactional(readOnly = false)
    public String adoptAnswer(Long questionId, Long answerId) {

        Optional<Question> question = questionRepository.findById(questionId);
        Optional<Answer> answer = answerRepository.findById(answerId);
        if(question.isPresent()){
            Question questionResult = question.get();
            if(questionResult.getStatus() == QuestionStatus.END){
                return "fail";
            }
            questionResult.changeStatus(QuestionStatus.END);
        }else{
            return "fail";
        }
        answer.ifPresent(value -> value.changeAdoption(true));
        return "success";
    }

    @Override
    public List<AnswerDTO> answer(Long questionId) {
        return answerRepository.findAllByQuestionIdOrderByAdoptedDesc(questionId).stream()
                .map(Answer::entityToAnswerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public long getCountByQuestionID(Long questionId) {
        return answerRepository.countByQuestionId(questionId);
    }

    @Override
    public List<BestAdoptedDTO> findBestAdopted() {
        List<BestAdoptedDTO> adpteds = answerRepository.findAllBestAdopted();
        List<BestAdoptedDTO> result = new ArrayList<>();
        for (int i = 0; i < Math.min(adpteds.size(), 5); i++) {
            result.add(adpteds.get(i));
        }
        return result;
    }

}
