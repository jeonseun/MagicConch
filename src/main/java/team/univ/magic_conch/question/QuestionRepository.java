package team.univ.magic_conch.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long>, QuestionRepositorySupport {

    Optional<Question> findById(Long questionNo);

    @Query("select q from Question q where lower(q.title) like lower(concat('%', concat(:title, '%')))")
    Page<Question> findAllByTitle(@Param("title") String title, Pageable pageable);

    @Query("select q from Question q where lower(q.user.username) like lower(concat('%', concat(:name, '%')))")
    Page<Question> findAllByUsername(@Param("name") String username, Pageable pageable);

    @EntityGraph(attributePaths = {"user"}, type = EntityGraph.EntityGraphType.LOAD)
    Page<Question> findAllByBundleId(Long bundleId, Pageable pageable);

    @Override
    long count();

    @Query("select count(q) from Question q where q.createTime between :beforeTime and :afterTime")
    long countbyTodayDate(@Param("beforeTime") LocalDateTime beforeTime,
                           @Param("afterTime") LocalDateTime afterTime);

    long countByStatus(QuestionStatus status);

    List<Question> findTop5ByStatusOrderByCreateTimeAsc(QuestionStatus status);

}
