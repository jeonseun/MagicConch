package team.univ.magic_conch.answer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.univ.magic_conch.answer.dto.CreateAnswerDTO;
import team.univ.magic_conch.question.Question;
import team.univ.magic_conch.question.QuestionRepository;
import team.univ.magic_conch.user.User;
import team.univ.magic_conch.user.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = false)
    public Answer answer(CreateAnswerDTO createAnswerDTO) {

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
        return answer;
    }
}
