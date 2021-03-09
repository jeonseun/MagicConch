package team.univ.magic_conch.team;

import lombok.Getter;
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
}
