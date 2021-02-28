package team.univ.magic_conch.answer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import team.univ.magic_conch.answer.dto.UpdateAnswerDTO;
import team.univ.magic_conch.question.Question;
import team.univ.magic_conch.user.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AnswerServiceTest {

    @Autowired
    private AnswerService answerService;
    @Autowired
    private AnswerRepository answerRepository;

    @Test
    public void 질문수정() throws Exception {
        //given
        User user = new User("test1", "testPw", "test");
        Question question = Question.builder()
                .title("title")
                .user(user)
                .build();
        Answer answer = Answer.builder()
                .content("이전 내용")
                .user(user)
                .question(question)
                .build();
        answerRepository.save(answer);
        UpdateAnswerDTO updateAnswerDTO = new UpdateAnswerDTO();
        updateAnswerDTO.setAnswerId(answer.getId());
        updateAnswerDTO.setContent("이후 내용");
        //when
        answerService.updateAnswer(updateAnswerDTO);
        //then
        Assertions.assertThat(answer.getContent()).isEqualTo("이후 내용");
    }

    @Test
    public void 질문삭제() throws Exception {
        //given
        User user = new User("test1", "testPw", "test");
        Question question = Question.builder()
                .title("title")
                .user(user)
                .build();
        Answer answer = Answer.builder()
                .content("이전 내용")
                .user(user)
                .question(question)
                .build();
        answerRepository.save(answer);
        //when
        answerService.deleteAnswer(answer.getId());
        //then
        Optional<Answer> result = answerRepository.findById(answer.getId());
        if(!result.isEmpty())
            Assertions.fail("질문이 삭제되지 않았습니다.");
    }
}