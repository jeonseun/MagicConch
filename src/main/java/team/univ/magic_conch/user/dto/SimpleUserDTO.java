package team.univ.magic_conch.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimpleUserDTO {

    private String username;
    private String profileImg;

}
