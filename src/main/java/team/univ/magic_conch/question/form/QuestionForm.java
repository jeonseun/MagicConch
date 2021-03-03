package team.univ.magic_conch.question.form;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class QuestionForm {

    private Long questionId;
    @NotBlank(message = "제목 작성은 필수 입니다.")
    private String title;
    @NotBlank(message = "내용 작성은 필수 입니다.")
    private String content;
    private Long bundleId;
    @NotBlank(message = "태그 선택을 필수 입니다.")
    private String tagName;

}
