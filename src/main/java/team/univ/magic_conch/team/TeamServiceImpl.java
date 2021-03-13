package team.univ.magic_conch.team;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.univ.magic_conch.team.exception.TeamNotFoundException;
import team.univ.magic_conch.user.User;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TeamServiceImpl implements TeamService{

    private final TeamRepository teamRepository;

    @Override
    public Team createTeam(User owner, String teamName, String description) {
        Team team = Team.builder()
                .teamName(teamName)
                .user(owner)
                .description(description)
                .build();
        return teamRepository.save(team);
    }

    @Override
    public List<Team> getOwnTeam(String username) {
        return teamRepository.findAllByOwnerUsername(username);
    }

    @Override
    public List<Team> getMyTeam(String username) {
        return teamRepository.findAllByUsername(username);
    }

    @Override
    public Team getTeam(Long teamId) {
        return teamRepository.findById(teamId).orElseThrow(TeamNotFoundException::new);
    }
}
