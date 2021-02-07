package team.univ.magic_conch.question;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import team.univ.magic_conch.follow.Follow;
import team.univ.magic_conch.follow.FollowRepository;
import team.univ.magic_conch.question.dto.QuestionDetailDTO;
import team.univ.magic_conch.question.dto.QuestionListDTO;
import team.univ.magic_conch.tag.Tag;
import team.univ.magic_conch.tag.TagRepository;
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
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private FollowRepository followRepository;
    
    @Test
    public void 질문하기() throws Exception {
        //given
        User user = User.builder()
                .username("테스터")
                .build();
        userRepository.save(user);
        Question question = Question.builder()
                .title("제목")
                .content("본문")
                .user(user)
                .build();
        //when
        questionService.questionForm(question);
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
        Tag tag = Tag.builder()
                .name("JAVA")
                .build();
        tagRepository.save(tag);
        Question question = Question.builder()
                .title("제목")
                .content("본문")
                .user(user)
                .tag(tag)
                .build();
        questionRepository.save(question);
        QuestionDetailDTO expect = question.entityToQuestionDetailDto();
        Long id = questionRepository.findAll().get(0).getId();
        //when
        QuestionDetailDTO result = questionService.questionDetail(id);
        //then
        Assertions.assertThat(result).as("해당 질문과 내용이 다릅니다")
                .usingRecursiveComparison()
                .isEqualTo(expect);
    }
    
    @Test
    public void 검색하기() throws Exception {
        //given
        List<QuestionListDTO> expects = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            User user = User.builder()
                    .username("tester" + i)
                    .build();
            Tag tag = Tag.builder()
                    .name("JAVA" + i)
                    .build();
            userRepository.save(user);
            tagRepository.save(tag);
            Question question = Question.builder()
                    .title("TestTitle")
                    .user(user)
                    .tag(tag)
                    .build();
            questionRepository.save(question);
            expects.add(question.entityToQuestionListDto());
        }
        //when
        List<QuestionListDTO> resultTitle
                = questionService.questionAllByTitleOrUsernameOrTagName("TestTitle", null, null, new PageRequestDTO(1)).getDtoList();
        List<QuestionListDTO> resultUsername
                = questionService.questionAllByTitleOrUsernameOrTagName(null, "tester0", null, new PageRequestDTO(1)).getDtoList();
        List<QuestionListDTO> resultTagName
                = questionService.questionAllByTitleOrUsernameOrTagName(null, null, "JAVA1", new PageRequestDTO(1)).getDtoList();
        List<QuestionListDTO> resultTitleAndTagName
                = questionService.questionAllByTitleOrUsernameOrTagName("TestTitle", null, "JAVA0", new PageRequestDTO(1)).getDtoList();
        List<QuestionListDTO> resultTitleAndTagNameAndTagName
                = questionService.questionAllByTitleOrUsernameOrTagName("TestTitle", "tester0", "JAVA0", new PageRequestDTO(1)).getDtoList();
        List<QuestionListDTO> resultTitleAndTagNameAndTagName2
                = questionService.questionAllByTitleOrUsernameOrTagName("TestTitle", "tester0", "JAVA1", new PageRequestDTO(1)).getDtoList();
        //then
        Assertions.assertThat(resultTitle)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .as("제목 검색이 일치하지 않습니다.")
                .isEqualTo(expects);
        Assertions.assertThat(resultUsername.size() == 1 ? resultUsername.get(0) : resultUsername)
                .as("이름 검색이 일치하지 않습니다.")
                .usingRecursiveComparison()
                .isEqualTo(expects.get(0));
        Assertions.assertThat(resultTagName.size() == 1 ? resultTagName.get(0) : resultTagName)
                .as("태그 검색이 일치하지 않습니다.")
                .usingRecursiveComparison()
                .isEqualTo(expects.get(1));
        Assertions.assertThat(resultTitleAndTagName.size() == 1 ? resultTitleAndTagName.get(0) : resultTitleAndTagName)
                .as("제목 + 태그 검색이 일치하지 않습니다.")
                .usingRecursiveComparison()
                .isEqualTo(expects.get(0));
        Assertions.assertThat(resultTitleAndTagNameAndTagName.size() == 1 ? resultTitleAndTagNameAndTagName.get(0) : resultTitleAndTagNameAndTagName)
                .as("제목 + 이름 + 태그 검색이 일치하지 않습니다.")
                .usingRecursiveComparison()
                .isEqualTo(expects.get(0));
        Assertions.assertThat(resultTitleAndTagNameAndTagName2)
                .as("해당 검색은 존재하면 안됩니다.")
                .isEmpty();
    }
    
    @Test
    public void 팔로우대상_질문목록() throws Exception {
        //given
        List<Question> expect = new ArrayList<>();
        User user = User.builder()
                .username("test").build();
        Tag tag = Tag.builder().name("자바").build();
        tagRepository.save(tag);
        userRepository.save(user);
        for (int i = 0; i < 10; i++) {
            User user2 = User.builder().username("test" + i).build();
            userRepository.save(user2);
            followRepository.save(new Follow(user, user2));
            Question question = Question.builder()
                    .title("제목")
                    .content("내용")
                    .tag(tag)
                    .user(user2)
                    .build();
            questionRepository.save(question);
            expect.add(question);
        }
        //when
        Page<Question> result = questionRepository.findAllByFollowUsername("test", null, null, null, new PageRequestDTO().getPageable());
        //then
        Assertions.assertThat(result.getContent())
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(expect);
    }
    
}