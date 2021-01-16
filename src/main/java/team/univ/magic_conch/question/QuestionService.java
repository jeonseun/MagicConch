package team.univ.magic_conch.question;

import team.univ.magic_conch.utils.page.PageRequestDTO;

import java.util.List;
import java.util.Optional;

public interface QuestionService {

    public void question();
    public void createQuestion(Question question);
    public Optional<Question> questionDetail(int questionNo);
    public List<Question> questionAll(PageRequestDTO pageRequestDTO);
    public List<Question> questionAllByUsername(String username, PageRequestDTO pageRequestDTO);
    public List<Question> questionAllByQuestionName(String questionname, PageRequestDTO pageRequestDTO);

}
