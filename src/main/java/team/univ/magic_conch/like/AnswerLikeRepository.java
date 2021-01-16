package team.univ.magic_conch.like;

import org.springframework.data.jpa.repository.JpaRepository;
import team.univ.magic_conch.answer.Answer;

public interface AnswerLikeRepository extends JpaRepository<Answer, Long> {
}
