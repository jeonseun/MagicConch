package team.univ.magic_conch.auth.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import team.univ.magic_conch.auth.dto.JoinFormDTO;

import java.util.Objects;

@Component
public class PasswordMatchValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return JoinFormDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        JoinFormDTO joinFormDTO = (JoinFormDTO) target;
        if (!Objects.equals(joinFormDTO.getPassword(), joinFormDTO.getPasswordCheck())) {
            errors.rejectValue("passwordCheck",
                    "passwordMismatch",
                    "비밀번호가 일치하지 않습니다.");
        }
    }
}
