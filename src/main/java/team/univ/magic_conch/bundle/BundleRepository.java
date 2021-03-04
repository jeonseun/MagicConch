package team.univ.magic_conch.bundle;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BundleRepository extends JpaRepository<Bundle, Long> {

    @EntityGraph(attributePaths = {"tag"}, type = EntityGraph.EntityGraphType.LOAD)
    List<Bundle> findAllByUserUsername(@Param("username") String username);

    @EntityGraph(attributePaths = {"questions", "tag"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Bundle> findWithQuestionsAndTagById(Long id);

    @EntityGraph(attributePaths = {"tag"}, type = EntityGraph.EntityGraphType.LOAD)
    Page<Bundle> findAllByUserUsername(@Param("username") String username, Pageable pageable);

    // TODO 특정 번들에 몇명의 사용자가 질문을 올렸는지 판단 필요함 (담당하는 리포지토리는 바뀔 수 있음)
}

