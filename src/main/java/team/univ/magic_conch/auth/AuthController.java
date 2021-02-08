package team.univ.magic_conch.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import team.univ.magic_conch.auth.dto.JoinFormDTO;
import team.univ.magic_conch.auth.validator.IdDoubleCheckValidator;
import team.univ.magic_conch.auth.validator.PasswordMatchValidator;
import team.univ.magic_conch.user.UserService;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class AuthController {

    private final UserService userService;
    private final PasswordMatchValidator passwordMatchValidator;
    private final IdDoubleCheckValidator idDoubleCheckValidator;

    @InitBinder("joinFormDTO")
    public void initJoinValidator(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(passwordMatchValidator, idDoubleCheckValidator);
    }

    // login page return
    @RequestMapping(value = "/loginForm",
            method = {RequestMethod.GET, RequestMethod.POST})
    public String loginForm() {
        return "auth/loginForm";
    }

    // join page return
    @GetMapping("/joinForm")
    public String joinForm(Model model) {
        model.addAttribute("joinFormDTO", new JoinFormDTO());
        return "auth/joinForm";
    }

    // validate join data and then process join
    @PostMapping("/join")
    public String join(@Valid @ModelAttribute JoinFormDTO joinFormDTO,
                       BindingResult bindingResult) {

        // validation error
        if (bindingResult.hasErrors()) {
            return "auth/joinForm";
        }

        // validation success
        userService.join(joinFormDTO.getUsername(), joinFormDTO.getPassword(), joinFormDTO.getName());
        return "auth/joinSuccess";
    }

}
