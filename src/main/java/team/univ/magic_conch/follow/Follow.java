package team.univ.magic_conch.follow;

import lombok.Getter;
import team.univ.magic_conch.user.User;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Entity
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;
    private LocalDate createdDate;

    // 팔로우를 요청하는 회원
    @ManyToOne
    @JoinColumn(name = "user_from")
    private User userFrom;

    //
    @ManyToOne
    @JoinColumn(name = "user_to")
    private User userTo;
}
