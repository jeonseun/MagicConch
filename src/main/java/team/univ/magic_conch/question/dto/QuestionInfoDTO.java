package team.univ.magic_conch.question.dto;

import lombok.Builder;
import lombok.Getter;
import team.univ.magic_conch.question.QuestionStatus;
import team.univ.magic_conch.user.User;
import team.univ.magic_conch.user.dto.UserSimpleDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

/**
 * 번들 내의 질문 목록 만들때 사용되는 DTO
 */
@Getter
public class QuestionInfoDTO {

    private Long questionId;
    private String title;
    private int views;
    private LocalDateTime createdTime;
    private LocalDateTime lastModifiedTime;
    private String status;
    private UserSimpleDTO author;

    @Builder
    public QuestionInfoDTO(Long questionId, String title, int views, LocalDateTime createdTime, LocalDateTime lastModifiedTime, QuestionStatus status, UserSimpleDTO author) {
        this.questionId = questionId;
        this.title = title;
        this.views = views;
        this.createdTime = createdTime;
        this.lastModifiedTime = lastModifiedTime;
        this.status = status.toString();
        this.author = author;
    }
}
