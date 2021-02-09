package team.univ.magic_conch.answer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateAnswerDTO {

    String content;
    String username;
    Long questionId;

}
