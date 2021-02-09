package team.univ.magic_conch.question.dto;

import lombok.Builder;
import lombok.Getter;
import team.univ.magic_conch.question.QuestionStatus;

import java.time.LocalDateTime;

@Getter
public class QuestionPreviewDTO {
    private Long questionId;
    private LocalDateTime createdTime;
    private LocalDateTime lastModifyTime;
    private int views;
    private QuestionStatus status; // 답변 채택여부
    private String title;
    private int answerCount;

    @Builder
    public QuestionPreviewDTO(Long questionId, LocalDateTime createdTime, LocalDateTime lastModifyTime, int views, QuestionStatus status, String title, int answerCount) {
        this.questionId = questionId;
        this.createdTime = createdTime;
        this.lastModifyTime = lastModifyTime;
        this.views = views;
        this.status = status;
        this.title = title;
        this.answerCount = answerCount;
    }

    protected QuestionPreviewDTO() {
    }
}
