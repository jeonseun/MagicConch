package team.univ.magic_conch.team;

import team.univ.magic_conch.user.User;

import java.util.List;

public interface TeamUserService {

    /**
     * 팀에 다른 유저를 멤버로 추가
     * @param team
     * @param user
     * @return TeamUser Entity
     */
    TeamUser addMember(Team team, User user);

    /**
     * 해당 팀에 속한 유저 목록 반환
     * @param team
     * @return TeamUser Entity List
     */
    List<TeamUser> getMembers(Team team);
}
