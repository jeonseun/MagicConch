package team.univ.magic_conch.auth.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import team.univ.magic_conch.auth.dto.JoinFormDTO;
import team.univ.magic_conch.user.UserService;

@RequiredArgsConstructor
@Component
public class IdDoubleCheckValidator implements Validator {

    private final UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return JoinFormDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        JoinFormDTO joinFormDTO = (JoinFormDTO) target;
        if (userService.isUsernameDuplicate(joinFormDTO.getUsername())) {
            errors.rejectValue("username",
                    "idDuplicate",
                    "중복되는 ID 입니다.");
        }
    }
}
