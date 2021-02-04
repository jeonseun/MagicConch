package team.univ.magic_conch.follow;

import lombok.*;
import team.univ.magic_conch.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;
    private LocalDateTime createDate;

    // 팔로우를 요청하는 회원
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_from")
    private User userFrom;

    //
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_to")
    private User userTo;

    @Builder
    public Follow(User userFrom, User userTo) {
        this.createDate = LocalDateTime.now();
        this.userFrom = userFrom;
        this.userTo = userTo;
    }

}
