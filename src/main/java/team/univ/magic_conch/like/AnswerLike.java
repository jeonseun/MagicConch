package team.univ.magic_conch.like;

import lombok.Getter;
import team.univ.magic_conch.answer.Answer;
import team.univ.magic_conch.user.User;

import javax.persistence.*;

@Entity
@Getter
public class AnswerLike {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id")
    private Answer answer;

}
