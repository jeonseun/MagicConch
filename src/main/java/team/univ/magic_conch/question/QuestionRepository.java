package team.univ.magic_conch.question;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Question findById(int questionNo);
    List<Question> findAllByTitle(Pageable pageable);
    List<Question> findAllByUserId(Pageable pageable);
}
