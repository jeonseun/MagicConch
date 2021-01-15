package team.univ.magic_conch.question;

import lombok.Getter;
import team.univ.magic_conch.answer.Answer;
import team.univ.magic_conch.bundle.Bundle;
import team.univ.magic_conch.tag.Tag;
import team.univ.magic_conch.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Question {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    private String title;
    private String content;
    private int view;

    private LocalDateTime createTime;
    private LocalDateTime lastModifyTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bundle_id")
    private Bundle bundle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answers = new ArrayList<>();

    public void addAnswer(Answer answer) {
        this.answers.add(answer);
        answer.changeQuestion(this);
    }

    public void changeBundle(Bundle bundle) {
        if(this.bundle != bundle){
            this.bundle = bundle;
        }
    }
}
