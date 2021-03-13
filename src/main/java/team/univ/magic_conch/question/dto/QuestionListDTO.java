package team.univ.magic_conch.question.dto;

import lombok.*;
import team.univ.magic_conch.question.QuestionStatus;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class QuestionListDTO {

    private Long questionId;
    private String title;
    private String content;
    private int view;
    private String createTime;
    private Long beforeTime;
    private String username;
    private String profileImg;
    private String tagName;
    private String tagColor;
    private String status;

}
