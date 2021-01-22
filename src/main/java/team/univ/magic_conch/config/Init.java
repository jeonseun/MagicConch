package team.univ.magic_conch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import team.univ.magic_conch.bundle.Bundle;
import team.univ.magic_conch.bundle.BundleService;
import team.univ.magic_conch.question.Question;
import team.univ.magic_conch.question.QuestionService;
import team.univ.magic_conch.tag.Tag;
import team.univ.magic_conch.tag.TagRepository;
import team.univ.magic_conch.tag.TagService;
import team.univ.magic_conch.user.User;
import team.univ.magic_conch.user.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class Init implements CommandLineRunner {

    private final TagRepository tagRepository;
    private final UserService userService;
    private final BundleService bundleService;
    private final TagService tagService;
    private final QuestionService questionService;

    @Override
    public void run(String... args) throws Exception {

        String[] name = new String[]{"C", "C++", "JAVA", "PYTHON", "SPRING", "JPA", "DEFAULT"};
        String[] color = new String[]{"#FFB291", "#FF32B1", "#14148C", "#C964E2", "#147814", "#B246B2", "#8c8c8c"};

        for (int i = 0; i < name.length; i++) {
            tagRepository.save(
                    Tag.builder()
                            .name(name[i])
                            .color(color[i])
                            .createDate(LocalDate.now())
                            .build()
            );
        }

        User user = userService.join("q", "q", "Hajoo");
        bundleService.save(
                Bundle.builder()
                        .name("자바 질문방")
                        .user(user)
                        .tag(tagService.findByName("JAVA"))
                        .visibility("PRIVATE")
                        .build()
        );
        bundleService.save(
                Bundle.builder()
                        .name("스프링 질문방")
                        .user(user)
                        .tag(tagService.findByName("SPRING"))
                        .visibility("PUBLIC")
                        .build()
        );
        bundleService.save(
                Bundle.builder()
                        .name("파이썬 질문방")
                        .user(user)
                        .tag(tagService.findByName("PYTHON"))
                        .visibility("FRIEND")
                        .build()
        );

        for (int i = 0; i < 255; i++) {
            questionService.questionForm(
                    Question.builder()
                            .title("제목" + i)
                            .content("본문" + i)
                            .createTime(LocalDateTime.now())
                            .lastModifyTime(LocalDateTime.now())
                            .bundle(null)
                            .tag(tagService.findByName(name[i % 5]))
                            .user(user)
                            .build()
            );
        }
    }
}
