package team.univ.magic_conch.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserProfileDTO {
    private String username;
    private String name;
    private String profileImage;

    @Builder
    public UserProfileDTO(String username, String name, String profileImage) {
        this.username = username;
        this.name = name;
        this.profileImage = profileImage;
    }
}
