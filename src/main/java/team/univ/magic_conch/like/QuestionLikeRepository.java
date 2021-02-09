package team.univ.magic_conch.like;

import org.springframework.data.jpa.repository.JpaRepository;
import team.univ.magic_conch.question.Question;
import team.univ.magic_conch.user.User;

import java.util.Optional;

public interface QuestionLikeRepository extends JpaRepository<QuestionLike, Long> {

    Optional<QuestionLike> findByUserAndQuestion(User user, Question question);

}
