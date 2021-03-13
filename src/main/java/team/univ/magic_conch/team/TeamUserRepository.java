package team.univ.magic_conch.team;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamUserRepository extends JpaRepository<TeamUser, Long> {

    Optional<TeamUser> findByUserId(Long id);
}
