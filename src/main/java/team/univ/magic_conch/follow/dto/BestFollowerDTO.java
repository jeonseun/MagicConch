package team.univ.magic_conch.follow.dto;

import lombok.Getter;

@Getter
public class BestFollowerDTO {

    private String username;
    private String profileImg;
    private long followCnt;

    public BestFollowerDTO(String username, String profileImg, long followCnt) {
        this.username = username;
        this.profileImg = profileImg;
        this.followCnt = followCnt;
    }

}
