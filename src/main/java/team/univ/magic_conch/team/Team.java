package team.univ.magic_conch.team;

import lombok.Builder;
import lombok.Getter;
import team.univ.magic_conch.bundle.Bundle;
import team.univ.magic_conch.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Team {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "team_name")
    private String teamName;

    // 팀 관리자 (별도의 설정이 없으면 최초로 팀을 만든 유저가 됨)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 팀 멤버 (팀에 소속된 사용자)
    @OneToMany(mappedBy = "team")
    private List<TeamUser> teamUsers;

    // 팀과 연동된 번들
    @OneToMany(mappedBy = "team")
    private List<Bundle> bundles;

    @Builder
    public Team(String teamName, User user) {
        this.teamName = teamName;
        this.user = user;
        teamUsers = new ArrayList<>();
        bundles = new ArrayList<>();
    }

    protected Team() {}

    public void addTeamUser(TeamUser teamUser) {
        teamUsers.add(teamUser);
    }

    public void addBundle(Bundle bundle) {
        bundles.add(bundle);
        bundle.changeTeam(this);
    }
}
