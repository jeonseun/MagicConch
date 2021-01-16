package team.univ.magic_conch.like;

import org.springframework.data.jpa.repository.JpaRepository;
import team.univ.magic_conch.question.Question;

public interface QuestionLikeRepository extends JpaRepository<Question, Long> {
}
