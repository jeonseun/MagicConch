package team.univ.magic_conch.question;

import lombok.*;
import team.univ.magic_conch.answer.Answer;
import team.univ.magic_conch.bundle.Bundle;
import team.univ.magic_conch.question.dto.QuestionDetailDTO;
import team.univ.magic_conch.question.dto.QuestionInfoDTO;
import team.univ.magic_conch.question.dto.QuestionListDTO;
import team.univ.magic_conch.tag.Tag;
import team.univ.magic_conch.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Question {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long id;

    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;
    private int view;

    private LocalDateTime createTime;
    private LocalDateTime lastModifyTime;

    @Enumerated(EnumType.STRING)
    private QuestionStatus status;

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

    @Builder
    public Question(String title, String content, int view, User user, Bundle bundle, Tag tag) {
        this.title = title;
        this.content = content;
        this.view = view;
        this.createTime = LocalDateTime.now().withNano(0);
        this.lastModifyTime = LocalDateTime.now().withNano(0);
        this.user = user;
        this.bundle = bundle;
        this.tag = tag;
        this.status = QuestionStatus.ING;
    }

    public void addAnswer(Answer answer) {
        this.answers.add(answer);
        answer.changeQuestion(this);
    }

    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public void changeTag(Tag tag){
        this.tag = tag;
    }

    public void changeStatus(QuestionStatus status) { this.status = status; }

    public void changeCreateTime(LocalDateTime createTime) { this.createTime = createTime; }

    public void changeBundle(Bundle bundle) {
        if(this.bundle != bundle){
            this.bundle = bundle;
        }
    }

    public void changeView(){
        this.view ++;
    }

    public QuestionListDTO entityToQuestionListDto(){
        return QuestionListDTO.builder()
                .questionId(getId())
                .title(getTitle())
                .content(getContent())
                .view(getView())
                .createTime(getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .beforeTime((System.currentTimeMillis() - getCreateTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()) / 1000 / 60)
                .username(getUser().getUsername())
                .profileImg(getUser().getProfileImg())
                .tagName(getTag().getName())
                .tagColor(getTag().getColor())
                .status(getStatus().toString())
                .answerCnt(getAnswers().size())
                .build();
    }

    public QuestionDetailDTO entityToQuestionDetailDto(){
        return QuestionDetailDTO.builder()
                .questionId(getId())
                .title(getTitle())
                .content(getContent())
                .view(getView())
                .createTime(getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .lastModifyTime(getLastModifyTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .username(getUser().getUsername())
                .profileImg(getUser().getProfileImg())
                .tagName(getTag().getName())
                .tagColor(getTag().getColor())
                .tagImg(getTag().getImage())
                .bundleId(getBundle() != null ? getBundle().getId() : 0)
                .bundleName(getBundle() != null ? getBundle().getName() : null)
                .build();
    }

    public QuestionInfoDTO entityToQuestionInfoDTO() {
        return QuestionInfoDTO.builder()
                .questionId(getId())
                .author(getUser().entityToSimpleUserDto())
                .createdTime(getCreateTime())
                .lastModifiedTime(getLastModifyTime())
                .status(getStatus())
                .views(getView())
                .title(getTitle())
                .build();
    }

}
