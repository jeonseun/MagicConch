package team.univ.magic_conch.team.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TeamUserInfoDTO {
    private String username;
    private String name;
    private String profileImage;

    @Builder
    public TeamUserInfoDTO(String username, String name, String profileImage) {
        this.username = username;
        this.name = name;
        this.profileImage = profileImage;
    }
}
