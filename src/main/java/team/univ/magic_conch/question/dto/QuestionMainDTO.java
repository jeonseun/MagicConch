package team.univ.magic_conch.question.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.univ.magic_conch.user.dto.UserProfileDTO;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class QuestionMainDTO {

    private long total;
    private long todayTotal;
    private long noSolvedTotal;
    private long solvedTotal;
    private List<QuestionListDTO> questionList;

}