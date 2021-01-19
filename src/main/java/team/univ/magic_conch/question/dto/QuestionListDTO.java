package team.univ.magic_conch.question.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuestionListDTO {

    private Long questionId;
    private String title;
    private int view;
    private LocalDateTime createTime;
    private String username;
    private String tagName;

}
