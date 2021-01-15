package team.univ.magic_conch.answer;

import lombok.Getter;
import team.univ.magic_conch.question.Question;
import team.univ.magic_conch.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Answer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long id;

    private String content;
    private LocalDateTime createTime;
    private LocalDateTime lastModifyTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    public void changeQuestion(Question question){
        this.question = question;
    }

}
