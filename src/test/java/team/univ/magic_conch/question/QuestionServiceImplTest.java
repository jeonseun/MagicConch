package team.univ.magic_conch.question;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import team.univ.magic_conch.question.dto.QuestionDetailDTO;
import team.univ.magic_conch.question.dto.QuestionListDTO;
import team.univ.magic_conch.user.User;
import team.univ.magic_conch.user.UserRepository;
import team.univ.magic_conch.utils.page.PageRequestDTO;
import team.univ.magic_conch.utils.page.PageResultDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
class QuestionServiceImplTest {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserRepository userRepository;
    
    @Test
    public void 질문하기_GET() throws Exception {
        /* 구현 필요 */
    }
    
    @Test
    public void 질문하기_POST() throws Exception {
        //given
        User user = User.builder()
                .username("테스터")
                .build();
        userRepository.save(user);
        Question question = Question.builder()
                .title("제목")
                .content("본문")
                .user(user)
                .createTime(LocalDateTime.now())
                .build();
        //when
        questionService.createQuestion(question);
        //then
        Assertions.assertThat(questionRepository.findAll().size()).as("질문 생성 X").isEqualTo(1);
        Assertions.assertThat(questionRepository.findAll().get(0)).as("생성된 질문과 다릅니다").extracting("title").isEqualTo("제목");
    }
    
    @Test
    public void 상세질문보기() throws Exception {
        //given
        User user = User.builder()
                .username("테스터")
                .build();
        userRepository.save(user);
        Question question = Question.builder()
                .title("제목")
                .content("본문")
                .user(user)
                .createTime(LocalDateTime.now().withNano(0))
                .build();
        //when
        questionRepository.save(question);
        Long id = questionRepository.findAll().get(0).getId();
        QuestionDetailDTO expect = questionService.entityToQuestionDetailDto(question);
        QuestionDetailDTO result = questionService.questionDetail(id);
        //then
        Assertions.assertThat(result).as("해당 질문과 내용이 다릅니다").isEqualTo(expect);
    }

    @Test
    public void 전체질문목록보기() throws Exception {
        //given
        List<QuestionListDTO> expect = new ArrayList<>();
        int pageNo = 11;
        for (int i = 0; i < 105; i++) {
            User user = User.builder()
                    .username("테스터" + i)
                    .build();
            userRepository.save(user);
            Question question = Question.builder()
                    .title("제목" + i)
                    .content("본문" + i)
                    .user(user)
                    .createTime(LocalDateTime.now().minusDays(i).withNano(0))
                    .build();
            questionRepository.save(question);
            if(i >= (pageNo - 1) * 10 && i < pageNo * 10){
                expect.add(questionService.entityToQuestionListDto(question));
            }
        }
        //when
        List<QuestionListDTO> result = questionService.questionAll(new PageRequestDTO(pageNo)).getDtoList();
        PageResultDTO pageResultDTO = questionService.questionAll(new PageRequestDTO(pageNo));
        //then
        Assertions.assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expect);
    }

    @Test
    public void 질문제목검색() throws Exception {
        //given
        List<QuestionListDTO> expect = new ArrayList<>();
        for (int i = 0; i < 105; i++) {
            String title;
            if(i < 100) {
                title = "Title" + i;
            }else{
                title = "123Happy321";
            }
            User user = User.builder()
                    .username("테스터" + i)
                    .build();
            userRepository.save(user);
            Question question = Question.builder()
                    .title(title)
                    .content("본문" + i)
                    .user(user)
                    .createTime(LocalDateTime.now().minusDays(i).withNano(0))
                    .build();
            questionRepository.save(question);
            if(i >= 100){
                expect.add(questionService.entityToQuestionListDto(question));
            }
        }
        //when
        List<QuestionListDTO> result = questionService.questionAllByTitle("HAPPY", new PageRequestDTO()).getDtoList();
        //then
        Assertions.assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expect);
    }

    @Test
    public void 질문유저이름검색() throws Exception {
        //given
        List<QuestionListDTO> expect = new ArrayList<>();
        for (int i = 0; i < 105; i++) {
            String username;
            if(i < 100) {
                username = "user";
            }else{
                username = "123Abc321";
            }
            User user = User.builder()
                    .username(username)
                    .build();
            userRepository.save(user);
            Question question = Question.builder()
                    .title("제목")
                    .content("본문" + i)
                    .user(user)
                    .createTime(LocalDateTime.now().minusDays(i).withNano(0))
                    .build();
            questionRepository.save(question);
            if(i >= 100){
                expect.add(questionService.entityToQuestionListDto(question));
            }
        }
        //when
        List<QuestionListDTO> result = questionService.questionAllByUsername("ABc3", new PageRequestDTO()).getDtoList();
        //then
        Assertions.assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expect);
    }

}