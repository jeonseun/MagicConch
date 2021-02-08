package team.univ.magic_conch.auth.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class JoinDataDTO {

    private final String username;

    private final String password;

    private final String name;
}
