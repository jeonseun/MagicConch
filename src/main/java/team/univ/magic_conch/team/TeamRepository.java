package team.univ.magic_conch.team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("select t From Team t inner join t.teamUsers u where u.user.username = :username")
    List<Team> findAllByUsername(@Param("username") String username);

    @Query("select t From Team t where t.user.username = :username")
    List<Team> findAllByOwnerUsername(@Param("username") String username);
}
