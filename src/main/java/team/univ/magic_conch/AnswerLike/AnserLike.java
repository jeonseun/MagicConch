package team.univ.magic_conch.AnswerLike;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class AnserLike {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column("answer_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Answer answer;

}
