package team.univ.magic_conch.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserInfoDTO {
    private String aboutMe;
    private String interests;
    private String career;

    @Builder
    public UserInfoDTO(String aboutMe, String interests, String career) {
        this.aboutMe = aboutMe;
        this.interests = interests;
        this.career = career;
    }
}
