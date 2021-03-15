package team.univ.magic_conch.team;

import lombok.Builder;
import lombok.Getter;
import team.univ.magic_conch.team.dto.TeamUserInfoDTO;
import team.univ.magic_conch.user.User;

import javax.persistence.*;

@Getter
@Entity
public class TeamUser {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    @Builder
    public TeamUser(User user, Team team) {
        this.user = user;
        this.team = team;
    }

    protected TeamUser() {
    }

    public TeamUserInfoDTO entityToTeamUserInfoDTO() {
        return TeamUserInfoDTO.builder()
                .username(getUser().getUsername())
                .name(getUser().getName())
                .profileImage(getUser().getProfileImg())
                .build();
    }
}
