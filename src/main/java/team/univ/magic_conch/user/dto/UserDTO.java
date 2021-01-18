package team.univ.magic_conch.user.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class UserDTO {

    // 회원가입 입력폼 모델
    @Getter
    @Setter
    public static class JoinForm {
        @NotEmpty(message = "필수 입력입니다.")
        @Size(min = 6, max = 20, message = "6자 이상 20자 이하입니다.")
        @Pattern(regexp = "[a-zA-Z0-9]{6,20}", message = "숫자와 영문으로만 이용가능합니다")
        private String username;

        @NotEmpty(message = "필수 입력입니다.")
        @Size(min = 6, max = 20, message = "8자 이상 20자 이하입니다.")
        @Pattern(regexp = "[a-zA-Z0-9]{8,20}", message = "숫자와 영문으로만 이용가능합니다")
        private String password;
        private String passwordRepeat;

        @NotEmpty(message = "필수 입력입니다.")
        @Size(min = 2, max = 20, message = "2자 이상 20자 이하입니다.")
        private String personName;

        public JoinData toJoinData() {
            JoinData joinData = new JoinData();
            joinData.username = username;
            joinData.password = password;
            joinData.name = personName;
            return joinData;
        }
    }

    @Getter
    // 검증된 회원 가입 정보
    public static class JoinData {
        private String username;
        private String password;
        private String name;
    }

    @Getter
    @Setter
    public static class IdCheck {
        @NotEmpty
        @Size(min = 6, max = 20, message = "6자 이상 20자 이하입니다.")
        @Pattern(regexp = "[a-zA-Z0-9]{6,20}", message = "숫자와 영문으로만 이용가능합니다")
        private String id;
    }

}
