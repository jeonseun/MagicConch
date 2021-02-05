package team.univ.magic_conch.question.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class QuestionListDTO {

    private Long questionId;
    private String title;
    private int view;
    private LocalDateTime createTime;
    private String username;
    private String tagName;
    private String tagColor;

}
