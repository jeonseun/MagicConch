package team.univ.magic_conch.team;

import team.univ.magic_conch.bundle.Bundle;
import team.univ.magic_conch.user.User;

import java.util.List;

public interface TeamService {

    /**
     * 신규 팀 생성
     * @param owner
     * @param teamName
     * @param description
     * @return Team Entity
     */
    Team createTeam(User owner, String teamName, String description);

    /**
     * 자기 소유의 팀 조회
     * @param username
     * @return Team List
     */
    List<Team> getOwnTeam(String username);

    /**
     * 자기가 속한 팀 조회
     * @param username
     * @return Team List
     */
    List<Team> getMyTeam(String username);

    /**
     * 팀 ID로 팀 조회
     * @param teamId
     * @return Team Entity
     */
    Team getTeam(Long teamId);

    /**
     * 팀에 번들 추가
     * @param team
     * @param bundle
     * @return 번들이 추가된 상태의 Team Entity
     */
    Team addBundle(Team team, Bundle bundle);

}
