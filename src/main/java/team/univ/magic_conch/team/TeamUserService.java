package team.univ.magic_conch.team;

import team.univ.magic_conch.user.User;

public interface TeamUserService {

    /**
     * 팀에 다른 유저를 멤버로 추가
     * @param team
     * @param user
     * @return TeamUser Entity
     */
    TeamUser addMember(Team team, User user);
}
