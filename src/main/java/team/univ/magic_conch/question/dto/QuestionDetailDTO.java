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
public class QuestionDetailDTO {

    private Long questionId;
    private String title;
    private String content;
    private int view;
    private LocalDateTime createTime;
    private LocalDateTime lastModifyTime;
    private String username;
    private String tagName;
    private String tagColor;
    private Long bundleId;
    private String bundleName;

}
