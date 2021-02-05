package team.univ.magic_conch.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuestionRepositorySupport {

    Page<Question> findAllByTitleOrUsernameOrTagName(String title, String username, String tagName, Pageable pageable);
    Page<Question> findAllByFollowUsername(String username, Pageable pageable);

}
