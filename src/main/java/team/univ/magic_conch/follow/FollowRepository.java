package team.univ.magic_conch.follow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team.univ.magic_conch.user.User;
import team.univ.magic_conch.follow.dto.BestFollowerDTO;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    List<Follow> findAllByUserFrom(User user);
    List<Follow> findAllByUserTo(User user);
    Optional<Follow> findByUserFromAndUserTo(User userFrom, User userTo);

    @Query("select new team.univ.magic_conch.follow.dto.BestFollowerDTO(f.userTo.username, f.userTo.profileImg, count(f)) From Follow f group by f.userTo order by count(f) desc")
    List<BestFollowerDTO> findAllBestFollower();

}
