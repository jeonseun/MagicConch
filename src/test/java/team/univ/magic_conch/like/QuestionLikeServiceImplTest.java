package team.univ.magic_conch.like;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import team.univ.magic_conch.question.Question;
import team.univ.magic_conch.question.QuestionRepository;
import team.univ.magic_conch.user.User;
import team.univ.magic_conch.user.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class QuestionLikeServiceImplTest {

    @Autowired private QuestionLikeService questionLikeService;
    @Autowired private QuestionLikeRepository questionLikeRepository;
    @Autowired private QuestionRepository questionRepository;
    @Autowired private UserRepository userRepository;

    @Test
    public void 좋아요_취소() throws Exception {
        //given
        User user = new User("test", "1234", "hajoo");
        Question question = Question.builder().title("제목").build();
        userRepository.save(user);
        questionRepository.save(question);
        //when, then
        QuestionLike result = questionLikeService.createQuestionLike("test", question.getId());
        Assertions.assertThat(result).isEqualTo(questionLikeRepository.findAll().get(0));
        questionLikeService.deleteQuestionLike("test", question.getId());
        Assertions.assertThat(questionLikeRepository.findAll()).isEmpty();
    }

}