package team.univ.magic_conch.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long>, QuestionRepositorySupport {
    Optional<Question> findById(Long questionNo);

    @Query("select q from Question q where lower(q.title) like lower(concat('%', concat(:title, '%')))")
    Page<Question> findAllByTitle(@Param("title") String title, Pageable pageable);

    @Query("select q from Question q where lower(q.user.username) like lower(concat('%', concat(:name, '%')))")
    Page<Question> findAllByUsername(@Param("name") String username, Pageable pageable);

    long countByBundleId(Long bundleId);

    Page<Question> findAllByBundleId(Long bundleId, Pageable pageable);

}
