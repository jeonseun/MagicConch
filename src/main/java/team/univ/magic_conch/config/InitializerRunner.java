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
    }
}
