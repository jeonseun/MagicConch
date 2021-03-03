package team.univ.magic_conch.answer.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class CreateAnswerDTO {

    @NotBlank(message = "내용 작성은 필수 입니다.")
    String content;
    @NotBlank
    String username;
    @NotNull
    Long questionId;

}
