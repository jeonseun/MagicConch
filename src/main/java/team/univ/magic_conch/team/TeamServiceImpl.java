package team.univ.magic_conch.team;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.univ.magic_conch.bundle.Bundle;
import team.univ.magic_conch.team.exception.TeamNotFoundException;
import team.univ.magic_conch.user.User;

@RequiredArgsConstructor
@Service
public class TeamServiceImpl implements TeamService{

    private final TeamRepository teamRepository;

    @Override
    public Team createTeam(Bundle bundle, User user) {
        return teamRepository.save(Team.builder().bundle(bundle).user(user).build());
    }

    @Override
    public Team getByBundleId(Long bundleId) {
        return teamRepository.findByBundleId(bundleId).orElseThrow(TeamNotFoundException::new);
    }
}
