package team.univ.magic_conch.answer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import team.univ.magic_conch.answer.dto.BestAdoptedDTO;
import team.univ.magic_conch.answer.dto.UpdateAnswerDTO;
import team.univ.magic_conch.question.Question;
import team.univ.magic_conch.question.QuestionRepository;
import team.univ.magic_conch.question.QuestionStatus;
import team.univ.magic_conch.user.User;
import team.univ.magic_conch.user.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AnswerServiceTest {

    @Autowired
    private AnswerService answerService;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserRepository userRepository;

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
        if(result.isPresent())
            Assertions.fail("질문이 삭제되지 않았습니다.");
    }

    @Test
    public void 질문채택() throws Exception {
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
        questionRepository.save(question);
        //when
        answerService.adoptAnswer(question.getId(), answer.getId());
        //then
        Assertions.assertThat(question.getStatus()).isEqualTo(QuestionStatus.END);
        Assertions.assertThat(answer.isAdopted()).isEqualTo(true);

    }

    @Test
    public void 채택탑5() throws Exception {
        //given
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            User user = User.builder().username("test" + i).build();
            userRepository.save(user);
            users.add(user);
        }
        for (int i = 0; i < 5; i++) {
            for (int j = i; j < 5; j++) {
                Answer answer = Answer.builder().user(users.get(i)).build();
                answer.changeAdoption(true);
                answerRepository.save(answer);
            }
        }
        //when
        List<BestAdoptedDTO> result = answerService.findBestAdopted();
        //then
        Assertions.assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("profileImg")
                .isEqualTo(Arrays.asList(
                   new BestAdoptedDTO("test0", null, 5L),
                   new BestAdoptedDTO("test1", null, 4L),
                   new BestAdoptedDTO("test2", null, 3L),
                   new BestAdoptedDTO("test3", null, 2L),
                   new BestAdoptedDTO("test4", null, 1L)
                ));
    }
}