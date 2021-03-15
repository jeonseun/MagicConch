package team.univ.magic_conch.follow.dto;

import lombok.Getter;

@Getter
public class BestFollowerDTO {

    private String username;
    private String profiileImg;
    private long followCnt;

    public BestFollowerDTO(String username, String profiileImg, long followCnt) {
        this.username = username;
        this.profiileImg = profiileImg;
        this.followCnt = followCnt;
    }

}
