package team.univ.magic_conch.like;

import team.univ.magic_conch.question.Question;
import team.univ.magic_conch.user.User;

import javax.persistence.*;

@Entity
public class QuestionLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_like_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}
