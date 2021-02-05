package team.univ.magic_conch.question.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class QuestionFollowDTO {

    private Long questionId;
    private String title;
    private String content;
    private LocalDateTime createTime;
    private Long bundleId;
    private String bundleName;
    private String tagName;
    private String tagColor;

}
