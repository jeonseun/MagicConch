package team.univ.magic_conch.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import team.univ.magic_conch.question.dto.QuestionSearchDTO;

import java.util.List;

public interface QuestionRepositorySupport {

    Page<Question> findAllByTitleOrUsernameOrTagName(QuestionSearchDTO questionSearchDTO);
    Page<Question> findAllByFollowUsername(String myname, QuestionSearchDTO questionSearchDTO);

}
