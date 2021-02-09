package team.univ.magic_conch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import team.univ.magic_conch.bundle.Bundle;
import team.univ.magic_conch.bundle.BundleService;
import team.univ.magic_conch.follow.FollowService;
import team.univ.magic_conch.question.Question;
import team.univ.magic_conch.question.QuestionService;
import team.univ.magic_conch.tag.Tag;
import team.univ.magic_conch.tag.TagRepository;
import team.univ.magic_conch.tag.TagService;
import team.univ.magic_conch.user.User;
import team.univ.magic_conch.user.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Init implements CommandLineRunner {

    private final TagRepository tagRepository;
    private final UserService userService;
    private final BundleService bundleService;
    private final TagService tagService;
    private final QuestionService questionService;
    private final FollowService followService;

    @Override
    public void run(String... args) throws Exception {

        String[] name = new String[]{"C", "C++", "JAVA", "PYTHON", "SPRING", "JPA", "DEFAULT"};
        String[] color = new String[]{"#FFB291", "#FF32B1", "#14148C", "#C964E2", "#147814", "#B246B2", "#8c8c8c"};

        /* tag 추가 */
        for (int i = 0; i < name.length; i++) {
            tagRepository.save(
                    Tag.builder()
                            .name(name[i])
                            .color(color[i])
                            .createDate(LocalDate.now())
                            .build()
            );
        }

        List<User> userList= new ArrayList<>();
        /* user 추가 */
        User user = userService.join("q", "q", "Hajoo");

        for (int i = 0; i < 5; i++) {
            userList.add(userService.join(String.valueOf((char)('a' + i)), String.valueOf((char)('a' + i)), "Hajoo" + (i + 2)));
        }

        /* bundle 추가 */
        Bundle bundle1 = Bundle.builder()
                .name("자바 질문방")
                .user(user)
                .tag(tagService.findByName("JAVA"))
                .visibility("PRIVATE")
                .build();
        bundleService.save(bundle1);

        Bundle bundle2 = Bundle.builder()
                .name("스프링 질문방")
                .user(user)
                .tag(tagService.findByName("SPRING"))
                .visibility("PUBLIC")
                .build();
        bundleService.save(bundle2);

        Bundle bundle3 = Bundle.builder()
                .name("파이썬 질문방")
                .user(user)
                .tag(tagService.findByName("PYTHON"))
                .visibility("FRIEND")
                .build();
        bundleService.save(bundle3);
        /* question 추가 */
        for (int i = 0; i < 255; i++) {
//            Thread.sleep(10);
            if(i < 50) {
                questionService.createQuestion(
                        Question.builder()
                                .title("제목" + i)
                                .content("본문" + i)
                                .bundle(null)
                                .tag(tagService.findByName(name[i % 7]))
                                .user(user)
                                .build()
                );
            }
            questionService.createQuestion(
                    Question.builder()
                            .title("제목" + (254 - i))
                            .content("본문" + (254 - i))
                            .bundle(null)
                            .tag(tagService.findByName(name[i % 7]))
                            .user(userList.get(i % 5))
                            .build()
            );
        }

        /* follow 추가 */
        for (int i = 0; i < 5; i++) {
            followService.addFollow(user, userList.get(i));
        }

        followService.addFollow(userList.get(0), user);
        followService.addFollow(userList.get(1), user);
        followService.addFollow(userList.get(2), user);

        /* 번들에 속하는 질문 생성 */
        /* question 추가 */
        for (int i = 0; i < 120; i++) {
            if(i < 50) {
                questionService.createQuestion(
                        Question.builder()
                                .title("제목" + i)
                                .content("본문" + i)
                                .bundle(bundle1)
                                .tag(tagService.findByName(name[i % 7]))
                                .user(user)
                                .build()
                );
            }
            questionService.createQuestion(
                    Question.builder()
                            .title("제목" + (254 - i))
                            .content("본문" + (254 - i))
                            .bundle(bundle2)
                            .tag(tagService.findByName(name[i % 7]))
                            .user(userList.get(i % 5))
                            .build()
            );
        }
    }
}
