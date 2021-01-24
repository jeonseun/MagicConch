package team.univ.magic_conch.bundle;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team.univ.magic_conch.bundle.dto.BundleDTO;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface BundleRepository extends JpaRepository<Bundle, Long> {

    @EntityGraph(attributePaths = {"questions", "tag"}, type = EntityGraph.EntityGraphType.LOAD)
    List<Bundle> findAllByUserUsername(String username);

    @EntityGraph(attributePaths = {"questions", "tag"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Bundle> findWithQuestionsAndTagById(Long id);
}

