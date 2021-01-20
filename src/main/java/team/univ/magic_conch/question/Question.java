package team.univ.magic_conch.question;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.univ.magic_conch.answer.Answer;
import team.univ.magic_conch.bundle.Bundle;
import team.univ.magic_conch.question.dto.QuestionDetailDTO;
import team.univ.magic_conch.question.dto.QuestionListDTO;
import team.univ.magic_conch.question.form.QuestionForm;
import team.univ.magic_conch.tag.Tag;
import team.univ.magic_conch.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    public QuestionListDTO entityToQuestionListDto(){
        return QuestionListDTO.builder()
                .questionId(getId())
                .title(getTitle())
                .view(getView())
                .createTime(getCreateTime())
                .username(getUser().getUsername())
                .tagName(getTag().getName())
                .tagColor(getTag().getColor())
                .build();
    }

    public QuestionDetailDTO entityToQuestionDetailDto(){
        return QuestionDetailDTO.builder()
                .questionId(getId())
                .title(getTitle())
                .content(getContent())
                .view(getView())
                .createTime(getCreateTime())
                .lastModifyTime(getLastModifyTime())
                .username(getUser().getUsername())
                .tagName(getTag().getName())
                .bundleId(getBundle().getId())
                .build();
    }

}
