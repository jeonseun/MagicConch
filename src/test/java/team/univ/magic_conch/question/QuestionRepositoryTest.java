package team.univ.magic_conch.question;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team.univ.magic_conch.bundle.Bundle;
import team.univ.magic_conch.tag.Tag;
import team.univ.magic_conch.user.User;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class QuestionRepositoryTest {

    @Autowired EntityManager em;
    @Autowired QuestionRepository questionRepository;

    @Test
    public void 번들_ID로_질문조회() {
        // given
        // setup
        User user1 = User.builder().build();
        em.persist(user1);
        Tag tag = Tag.builder()
                .name("TEST")
                .color("#00B74A")
                .build();
        em.persist(tag);
        Bundle bundle1 = Bundle.builder()
                .name("bundle1")
                .user(user1)
                .tag(tag)
                .visibility("PRIVATE")
                .build();
        em.persist(bundle1);
        Bundle bundle2 = Bundle.builder()
                .name("bundle2")
                .user(user1)
                .tag(tag)
                .visibility("PUBLIC")
                .build();
        em.persist(bundle2);

        for (int i = 0; i < 3; i++) {
            Question question = Question.builder().build();
            question.changeBundle(bundle1);
            em.persist(question);
        }

        for (int i = 0; i < 8; i++) {
            Question question = Question.builder().build();
            question.changeBundle(bundle2);
            em.persist(question);
        }
        System.out.println("=================== query divider ===================");
        System.out.println("=================== query divider ===================");
        System.out.println("=================== query divider ===================");
        System.out.println("=================== query divider ===================");
        // when
        List<Question> findQuestions1 = questionRepository.findByBundleId(bundle1.getId());
        List<Question> findQuestions2 = questionRepository.findByBundleId(bundle2.getId());

        // then
        Assertions.assertThat(findQuestions1.size()).isEqualTo(3);
        Assertions.assertThat(findQuestions2.size()).isEqualTo(8);
        Assertions.assertThat(findQuestions1.get(0).getBundle()).isEqualTo(bundle1);
        Assertions.assertThat(findQuestions2.get(0).getBundle()).isEqualTo(bundle2);
    }
}