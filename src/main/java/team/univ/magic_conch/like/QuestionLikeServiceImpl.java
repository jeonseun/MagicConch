package team.univ.magic_conch.like;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.univ.magic_conch.question.Question;
import team.univ.magic_conch.question.QuestionRepository;
import team.univ.magic_conch.user.User;
import team.univ.magic_conch.user.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionLikeServiceImpl implements QuestionLikeService{

    private final QuestionLikeRepository questionLikeRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    @Override
    @Transactional(readOnly = false)
    public QuestionLike createQuestionLike(String username, Long questionId) {
        Optional<User> user = userRepository.findByUsername(username);
        Optional<Question> question = questionRepository.findById(questionId);
        QuestionLike questionLike = null;
        if(user.isPresent() && question.isPresent()){
            questionLike = new QuestionLike(user.get(), question.get());
            questionLikeRepository.save(questionLike);
        }
        return questionLike;
    }

    @Override
    @Transactional(readOnly = false)
    public QuestionLike deleteQuestionLike(String username, Long questionId) {
        Optional<User> user = userRepository.findByUsername(username);
        Optional<Question> question = questionRepository.findById(questionId);
        QuestionLike questionLike = null;
        if(user.isPresent() && question.isPresent()){
            questionLike = questionLikeRepository.findByUserAndQuestion(user.get(), question.get()).orElseGet(null);
            questionLikeRepository.delete(questionLike);
        }
        return questionLike;
    }

    @Override
    public Boolean isQuestionLike(String username, Long questionId) {
        Optional<User> user = userRepository.findByUsername(username);
        Optional<Question> question = questionRepository.findById(questionId);
        if(user.isPresent() && question.isPresent()) {
            return questionLikeRepository.findByUserAndQuestion(user.get(), question.get()).isEmpty();
        }else{
            return true;
        }
    }
}
