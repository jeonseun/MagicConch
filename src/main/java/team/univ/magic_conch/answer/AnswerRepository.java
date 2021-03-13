package team.univ.magic_conch.answer;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    long countByQuestionId(Long questionId);

    void deleteById(Long answerId);

    @EntityGraph(attributePaths = {"user"}, type = EntityGraph.EntityGraphType.LOAD)
    Optional<Answer> findById(Long answerId);

    @EntityGraph(attributePaths = {"user"}, type = EntityGraph.EntityGraphType.LOAD)
    List<Answer> findAllByQuestionIdOrderByAdoptedDesc(Long questionId);
}
