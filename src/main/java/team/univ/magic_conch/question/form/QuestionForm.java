package team.univ.magic_conch.question.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuestionForm {

    private Long questionId;
    private String title;
    private String content;
    private Long bundleId;
    private String tagName;

}
