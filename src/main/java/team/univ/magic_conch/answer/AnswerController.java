package team.univ.magic_conch.answer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AnswerController {

    @PostMapping("/answer")
    public String answer() {
        return "";
    }
}
