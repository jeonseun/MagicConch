package team.univ.magic_conch.answer;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.univ.magic_conch.answer.dto.AnswerDTO;
import team.univ.magic_conch.question.Question;
import team.univ.magic_conch.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long id;

    @Column(length = 512)
    private String content;
    private LocalDateTime createTime;
    private LocalDateTime lastModifyTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    public void changeQuestion(Question question) {
        this.question = question;
    }

    @Builder
    public Answer(String content, User user, Question question) {
        this.content = content;
        this.createTime = LocalDateTime.now();
        this.lastModifyTime = LocalDateTime.now();
        this.user = user;
        this.question = question;
    }

    public AnswerDTO entityToAnswerDTO(){
        return AnswerDTO.builder()
                .username(user.getUsername())
                .profileImg(user.getProfileImg())
                .answerId(getId())
                .content(getContent())
                .createTime(getCreateTime().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH.mm")))
                .build();
    }

}
