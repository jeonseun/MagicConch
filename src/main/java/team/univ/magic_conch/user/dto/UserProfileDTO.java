package team.univ.magic_conch.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserProfileDTO {
    private String username;
    private String name;
    private String image;
    private boolean followed;

    @Builder
    public UserProfileDTO(String username, String name, String image, boolean followed) {
        this.username = username;
        this.name = name;
        this.image = image;
        this.followed = followed;
    }
}
