package team.univ.magic_conch.BundleLike;

import lombok.Getter;
import team.univ.magic_conch.Bundle.Bundle;

import javax.persistence.*;

@Entity
@Getter
public class BundleLike {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bundle_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Bundle bundle;

}
