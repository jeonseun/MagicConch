package team.univ.magic_conch.Answer;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Answer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long id;

    private String content;
    private LocalDateTime createdTime;
    private LocalDateTime lastModifiedTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

    public void changeQuestion(Question question){
        this.question = question;
    }

}
