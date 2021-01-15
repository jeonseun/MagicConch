package team.univ.magic_conch.like;

import lombok.Getter;
import team.univ.magic_conch.bundle.Bundle;
import team.univ.magic_conch.user.User;

import javax.persistence.*;

@Entity
@Getter
public class BundleLike {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bundle_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bundle_id")
    private Bundle bundle;

}
