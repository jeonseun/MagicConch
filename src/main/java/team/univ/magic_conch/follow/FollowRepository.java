package team.univ.magic_conch.follow;

import org.springframework.data.jpa.repository.JpaRepository;
import team.univ.magic_conch.user.User;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    List<Follow> findAllByUserFrom(User user);
    List<Follow> findAllByUserTo(User user);
    Optional<Follow> findByUserFromAndUserTo(User userFrom, User userTo);

}
