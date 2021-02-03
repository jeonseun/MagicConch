package team.univ.magic_conch.answer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import team.univ.magic_conch.question.Question;
import team.univ.magic_conch.user.User;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
class AnswerServiceTest {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private EntityManager em;

    private User sampleUser1;
    private Question sampleQuestion1;

    @BeforeEach
    public void setSampleData() {
        if(sampleUser1 == null && sampleQuestion1 == null) {
            sampleUser1 = new User();
            sampleQuestion1 = Question.builder().build();
            em.persist(sampleUser1);
            em.persist(sampleQuestion1);
        }
    }

    @Test
    public void 답변하기() throws Exception {
        // given
        String content = "답변입니다.";
        Long authorId = sampleUser1.getId();
        Long questionId = sampleQuestion1.getId();

        // when
        Answer newAnswer = answerService.answer(content, sampleUser1, sampleQuestion1);

        // then
        assertEquals(authorId, newAnswer.getUser().getId());
        assertEquals(questionId, newAnswer.getQuestion().getId());
        assertEquals(content, newAnswer.getContent());
    }
}