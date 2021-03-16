package team.univ.magic_conch.answer.dto;

import lombok.Getter;

@Getter
public class BestAdoptedDTO {

    private String username;
    private String profileImg;
    private long adoptedCnt;

    public BestAdoptedDTO(String username, String profileImg, long adoptedCnt) {
        this.username = username;
        this.profileImg = profileImg;
        this.adoptedCnt = adoptedCnt;
    }

}
