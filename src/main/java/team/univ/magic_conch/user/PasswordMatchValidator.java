package team.univ.magic_conch.user;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import team.univ.magic_conch.user.dto.UserDTO;

import java.util.Objects;

public class PasswordMatchValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.JoinForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        UserDTO.JoinForm joinForm = (UserDTO.JoinForm) target;
        if (!Objects.equals(joinForm.getPassword(), joinForm.getPasswordRepeat())) {
            errors.rejectValue("passwordRepeat",
                    "passwordMismatch",
                    "비밀번호가 일치하지 않습니다.");
        }
    }
}
