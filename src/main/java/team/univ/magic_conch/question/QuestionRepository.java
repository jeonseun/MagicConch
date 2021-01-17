package team.univ.magic_conch.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Optional<Question> findById(Long questionNo);
    Page<Question> findAllByTitle(String title, Pageable pageable);
    @Query("select q from Question q where q.user.name = :name")
    Page<Question> findAllByUsername(@Param("name") String username, Pageable pageable);
}
