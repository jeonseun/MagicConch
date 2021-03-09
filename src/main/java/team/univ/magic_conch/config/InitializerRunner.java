package team.univ.magic_conch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import team.univ.magic_conch.bundle.Bundle;
import team.univ.magic_conch.bundle.BundleRepository;
import team.univ.magic_conch.bundle.BundleService;
import team.univ.magic_conch.follow.FollowService;
import team.univ.magic_conch.question.Question;
import team.univ.magic_conch.question.QuestionService;
import team.univ.magic_conch.tag.Tag;
import team.univ.magic_conch.tag.TagRepository;
import team.univ.magic_conch.tag.TagService;
import team.univ.magic_conch.user.UserService;
import team.univ.magic_conch.bundle.AccessLevel;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class InitializerRunner implements CommandLineRunner {

    private final TagRepository tagRepository;
    private final UserService userService;
    private final BundleService bundleService;
    private final TagService tagService;
    private final QuestionService questionService;
    private final FollowService followService;
    private final BundleRepository bundleRepository;

    @Value("${custom.file.tag-image-path}")
    private String tagImagePath;

    // TODO 태그 생성 코드 정리 필요함 태그 이미지 추가로 인한 코드 안좋아짐
    @Override
    public void run(String... args) throws Exception {

        String[] name = new String[]{"C", "C++", "Java", "Python", "Spring", "Javascript", "DEFAULT"};
        String[] color = new String[]{"#39C0ED", "#F93154", "#1e90ff", "#C964E2", "#00B74A", "#FFA900", "#FBFBFB"};
        String[] tagImageUrl = {"c_icon.png",
                "cpp_icon.png", "java_icon.png",
                "python_icon.png", "spring_icon.jpg",
                "js_icon.png", "default_tag_icon.png"};
        /* tag 추가 */
        for (int i = 0; i < name.length; i++) {
            tagRepository.save(
                    Tag.builder()
                            .name(name[i])
                            .color(color[i])
                            .createDate(LocalDate.now())
                            .image(tagImagePath + "/" + tagImageUrl[i])
                            .build()
            );
        }

        /* 테스트용 사용자 생성 */
        String[] usernames = {"q", "w", "e", "r", "t", "y"};
        String testPwd = "q";
        String[] names = {"전세운", "하주현", "송민광", "김동현", "박지율", "심지훈"};

        for (int i = 0; i < 6; i++) {
            userService.join(usernames[i], testPwd, names[i]);
        }

        /* 테스트용 번들 생성 */
        String[] bundleNames = {"C Study", "C++ Study",
                "Java Study", "Python Study",
                "Spring Study", "JPA Study"};
        for (int i = 0; i < 6; i++) {
            Bundle bundle = bundleService.createBundle(bundleNames[i], tagService.findByName(name[i]), userService.getUser(usernames[i]),
                    i % 2 == 0 ? AccessLevel.PUBLIC : i % 3 == 0 ? AccessLevel.PRIVATE : AccessLevel.GROUP);
        }

        for (int i = 0; i < 60; i++) {
            Bundle bundle = bundleService.createBundle("번들" + i, tagService.findByName(name[i % 7]), userService.getUser("q"),
                    i % 2 == 0 ? AccessLevel.PUBLIC : i % 3 == 0 ? AccessLevel.PRIVATE : AccessLevel.GROUP);
        }

        /* 테스트용 질문 생성 */
        for (int i = 0; i < 120; i++) {
            questionService.createQuestion(Question.builder()
                    .bundle(bundleRepository.findAllByUserUsername(usernames[i % 6]).get(0))
                    .tag(bundleRepository.findAllByUserUsername(usernames[i % 6]).get(0).getTag())
                    .user(userService.getUser(usernames[i % 6]))
                    .title("질문 " + i + " 번")
                    .content("테스트용 질문 " + i + " 의 내용")
                    .build());
        }

        /* 한 번들에 여러 사용자의 질문 추가 */
        // C 번들에 추가
        for (int i = 0; i < 20; i++) {
            questionService.createQuestion(Question.builder()
                    .bundle(bundleRepository.findAllByUserUsername(usernames[0]).get(0))
                    .tag(bundleRepository.findAllByUserUsername(usernames[0]).get(0).getTag())
                    .user(userService.getUser(usernames[i % 6]))
                    .title("다중 이용자 번들 질문 " + i + " 번")
                    .content("테스트용 질문 " + i + " 의 내용")
                    .build());
        }


        followService.addFollow(userService.getUser("q"), userService.getUser("w"));
        followService.addFollow(userService.getUser("q"), userService.getUser("e"));
        followService.addFollow(userService.getUser("q"), userService.getUser("r"));
        followService.addFollow(userService.getUser("q"), userService.getUser("t"));
        followService.addFollow(userService.getUser("q"), userService.getUser("y"));

        followService.addFollow(userService.getUser("w"), userService.getUser("q"));
        followService.addFollow(userService.getUser("e"), userService.getUser("q"));
        followService.addFollow(userService.getUser("r"), userService.getUser("q"));
        followService.addFollow(userService.getUser("t"), userService.getUser("q"));
        followService.addFollow(userService.getUser("y"), userService.getUser("q"));


    }
}
