package team.univ.magic_conch.answer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.univ.magic_conch.question.Question;
import team.univ.magic_conch.user.User;

@RequiredArgsConstructor
@Transactional
@Service
public class AnswerServiceImpl implements AnswerService{

    private final AnswerRepository answerRepository;

    /**
     * 신규 답변 생성
     * 특정 질문에 새로운 답변을 생성하고 저장한다.
     *
     * @param content  답변 내용
     * @param user     답변 작성자
     * @param question 답변이 달린 질문
     * @return 생성된 답변 id (Long)
     */
    public Answer answer(String content, User user, Question question) {
        Answer newAnswer = Answer.newAnswer(content, user, question);
        answerRepository.save(newAnswer);
        return newAnswer;
    }

    @Override
    public long getCountByQuestionID(Long questionId) {
        return answerRepository.countByQuestionId(questionId);
    }
}
