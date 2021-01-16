package team.univ.magic_conch.question;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import team.univ.magic_conch.utils.page.PageRequestDTO;

import java.util.Optional;

@RestController
public class QuestionController {

    @GetMapping("/question")
    public String question(Model model){

        return "/question";
    }

    @PostMapping("/question")
    public String createQuestion(Model model){

        return "/question";
    }

    @GetMapping("/question/{questionNo}")
    public String questionDetail(Model model, @PathVariable Optional<Integer> questionNo){

        int num = questionNo.isPresent() ? questionNo.get() : 0;

        return "/question/" + num;
    }

    @GetMapping("question/list")
    public String questionList(Model model, PageRequestDTO pageRequestDTO,
                               @RequestParam(value = "user") Optional<String> userName,
                               @RequestParam(value = "question") Optional<String> questionName){

        if(userName.isPresent()){

        }
        else if(questionName.isPresent()){

        }

        return "/question/" + pageNo;
    }

}
