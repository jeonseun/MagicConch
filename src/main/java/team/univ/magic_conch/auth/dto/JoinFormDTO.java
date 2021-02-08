package team.univ.magic_conch.auth.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@ToString
@Setter
@Getter
public class JoinFormDTO {

    @NotEmpty(message = "필수 입력입니다.")
    @Size(min = 6, max = 20, message = "6자 이상 20자 이하입니다.")
    @Pattern(regexp = "[a-zA-Z0-9]{6,20}", message = "숫자와 영문으로만 이용가능합니다")
    private String username;

    @NotEmpty(message = "필수 입력입니다.")
    @Size(min = 6, max = 20, message = "8자 이상 20자 이하입니다.")
    @Pattern(regexp = "[a-zA-Z0-9]{8,20}", message = "숫자와 영문으로만 이용가능합니다")
    private String password;
    private String passwordCheck;

    @NotEmpty(message = "필수 입력입니다.")
    @Size(min = 2, max = 20, message = "2자 이상 20자 이하입니다.")
    private String name;

}
