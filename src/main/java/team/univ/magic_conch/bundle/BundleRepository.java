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

    List<Bundle> findAllByNameContaining(String bundleName);
}

