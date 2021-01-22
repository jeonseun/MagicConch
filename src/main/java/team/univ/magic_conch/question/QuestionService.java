package team.univ.magic_conch.question;

import team.univ.magic_conch.bundle.dto.BundleDropBoxDTO;
import team.univ.magic_conch.question.dto.QuestionDetailDTO;
import team.univ.magic_conch.question.dto.QuestionListDTO;
import team.univ.magic_conch.user.User;
import team.univ.magic_conch.utils.page.PageRequestDTO;
import team.univ.magic_conch.utils.page.PageResultDTO;

import java.util.List;

public interface QuestionService {

    public List<BundleDropBoxDTO> question(String name);
    public void questionForm(Question question);
    public QuestionDetailDTO questionDetail(Long questionNo);
    public PageResultDTO<QuestionListDTO, Question> questionAllByTitleOrUsernameOrTagName(String title, String username, String tagName, PageRequestDTO pageRequestDTO);
    public void plusViews(Long questionId);

}
