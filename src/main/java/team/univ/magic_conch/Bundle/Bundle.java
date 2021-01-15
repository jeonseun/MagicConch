package team.univ.magic_conch.Bundle;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Getter
public class Bundle {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bundle_id")
    private Long id;

    private String name;
    private String visibility;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Tag tag;
    @OneToMany(mappedBy = "question_id")
    private List<Question> questions = new ArrayList<>();

    public void addQuestion(Question question){
        this.questions.add(question);
        if (question.getQuestion() != this) {
            question.changeBundle(this);
        }
    }

}
