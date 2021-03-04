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
    @Column(name = "group_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bundle_id")
    private Bundle bundle;

    @Builder
    public Team(Bundle bundle, User user) {
        this.bundle = bundle;
        this.user = user;
    }

    protected Team() {}
}
