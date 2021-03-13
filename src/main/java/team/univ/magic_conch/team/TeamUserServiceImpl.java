package team.univ.magic_conch.team;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.univ.magic_conch.user.User;

@RequiredArgsConstructor
@Service
public class TeamUserServiceImpl implements TeamUserService{

    private final TeamUserRepository teamUserRepository;

    @Override
    public TeamUser addMember(Team team, User user) {
        return teamUserRepository.save(TeamUser.builder()
                .team(team)
                .user(user)
                .build());
    }
}
