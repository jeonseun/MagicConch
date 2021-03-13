package team.univ.magic_conch.answer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AnswerDTO {

    Long answerId;
    String username;
    String profileImg;
    String content;
    String createTime;
    boolean adopted;

}
