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
import team.univ.magic_conch.user.User;
import team.univ.magic_conch.user.UserService;
import team.univ.magic_conch.visibility.Visibility;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

        // 기존 샘플 데이터 생성 코드
//        List<User> userList = new ArrayList<>();
//        /* user 추가 */
//        User user = userService.join("q", "q", "Hajoo");
//
//        for (int i = 0; i < 5; i++) {
//            userList.add(userService.join(String.valueOf((char) ('a' + i)), String.valueOf((char) ('a' + i)), "Hajoo" + (i + 2)));
//        }
//
//        /* bundle 추가 */
//        Bundle bundle1 = Bundle.builder()
//                .name("자바 질문방")
//                .user(user)
//                .tag(tagService.findByName("Java"))
//                .visibility(Visibility.PRIVATE)
//                .build();
//        bundleRepository.save(bundle1);
//
//        Bundle bundle2 = Bundle.builder()
//                .name("스프링 질문방")
//                .user(user)
//                .tag(tagService.findByName("Spring"))
//                .visibility(Visibility.PUBLIC)
//                .build();
//        bundleRepository.save(bundle2);
//
//        Bundle bundle3 = Bundle.builder()
//                .name("파이썬 질문방")
//                .user(user)
//                .tag(tagService.findByName("Python"))
//                .visibility(Visibility.FRIEND)
//                .build();
//        bundleRepository.save(bundle3);
//
//        /* 페이징용 번들 추가 */
//        User tester1 = userService.join("tester1", "q", "tester1");
//        User tester2 = userService.join("tester2", "q", "tester2");
//
//        for (int i = 0; i < 1000; i++) {
//            Bundle bundle = Bundle.builder()
//                    .name("번들" + i + "번")
//                    .user(i % 2 == 0 ? tester1 : tester2)
//                    .visibility(i % 2 == 0 ? Visibility.FRIEND : Visibility.PUBLIC)
//                    .tag(tagService.findByName("Python"))
//                    .build();
//            bundleRepository.save(bundle);
//        }
//
//        /* question 추가 */
//        for (int i = 0; i < 255; i++) {
////            Thread.sleep(10);
//            if (i < 50) {
//                questionService.createQuestion(
//                        Question.builder()
//                                .title("제목" + i)
//                                .content("본문" + i)
//                                .bundle(null)
//                                .tag(tagService.findByName(name[i % 7]))
//                                .user(user)
//                                .build()
//                );
//            }
//            questionService.createQuestion(
//                    Question.builder()
//                            .title("제목" + (254 - i))
//                            .content("본문" + (254 - i))
//                            .bundle(null)
//                            .tag(tagService.findByName(name[i % 7]))
//                            .user(userList.get(i % 5))
//                            .build()
//            );
//        }
//
//        /* follow 추가 */
//        for (int i = 0; i < 5; i++) {
//            followService.addFollow(user, userList.get(i));
//        }
//
//        followService.addFollow(userList.get(0), user);
//        followService.addFollow(userList.get(1), user);
//        followService.addFollow(userList.get(2), user);
//
//        /* 번들에 속하는 질문 생성 */
//        /* question 추가 */
//        for (int i = 0; i < 120; i++) {
//            if (i < 50) {
//                questionService.createQuestion(
//                        Question.builder()
//                                .title("제목" + i)
//                                .content("본문" + i)
//                                .bundle(bundle1)
//                                .tag(tagService.findByName(name[i % 7]))
//                                .user(user)
//                                .build()
//                );
//            }
//            questionService.createQuestion(
//                    Question.builder()
//                            .title("제목" + (254 - i))
//                            .content("본문" + (254 - i))
//                            .bundle(bundle2)
//                            .tag(tagService.findByName(name[i % 7]))
//                            .user(userList.get(i % 5))
//                            .build()
//            );
//        }

        // 기존 샘플 데이터 생성 코드

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
            bundleService.create(bundleNames[i], tagService.findByName(name[i]), userService.getUser(usernames[i]),
                    i % 2 == 0 ? Visibility.PUBLIC : i % 3 == 0 ? Visibility.PRIVATE : Visibility.FRIEND);
        }

        for (int i = 0; i < 60; i++) {
            bundleService.create("번들" + i, tagService.findByName(name[i % 7]), userService.getUser("q"),
                    i % 2 == 0 ? Visibility.PUBLIC : i % 3 == 0 ? Visibility.PRIVATE : Visibility.FRIEND);
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
