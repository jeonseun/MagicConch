package team.univ.magic_conch.team.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TeamInfoDTO {

    private Long teamId;
    private String teamName;
    private String teamDescription;

    @Builder
    public TeamInfoDTO(Long teamId, String teamName, String teamDescription) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamDescription = teamDescription;
    }
}
