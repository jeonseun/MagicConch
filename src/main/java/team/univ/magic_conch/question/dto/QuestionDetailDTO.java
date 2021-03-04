package team.univ.magic_conch.question.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class QuestionDetailDTO {

    private Long questionId;
    private String title;
    private String content;
    private int view;
    private String createTime;
    private String lastModifyTime;
    private String username;
    private String tagName;
    private String tagColor;
    private String tagImg;
    private Long bundleId;
    private String bundleName;

}
