package team.univ.magic_conch.answer;

import lombok.Getter;
import team.univ.magic_conch.question.Question;
import team.univ.magic_conch.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public void changeQuestion(Question question) {
        this.question = question;
    }

    /**
     * 새로운 답변 생성 메소드
     * 사용자가 질문에 답변을 달 때 몇 개의 정보(내용, 작성자, 질문)는 받고
     * 몇 개의 정보(생성시간, 수정시간)는 메소드 내에서 생성하여 새로운 답변 엔티티를 생성한다.
     *
     * @param content  답변 본문
     * @param user     답변 작성자
     * @param question 답변이 달린 질문
     * @return 생성된 answer 객체 (Answer Entity)
     */
    public static Answer newAnswer(String content, User user, Question question) {
        Answer answer = new Answer();
        answer.content = content;
        answer.user = user;
        answer.question = question;
        answer.createTime = LocalDateTime.now();
        answer.lastModifyTime = LocalDateTime.now();
        return answer;
    }
}
