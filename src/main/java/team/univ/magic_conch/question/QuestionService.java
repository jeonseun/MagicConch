package team.univ.magic_conch.question;

import team.univ.magic_conch.bundle.dto.BundleDTO;
import team.univ.magic_conch.bundle.dto.BundleDropBoxDTO;
import team.univ.magic_conch.question.dto.QuestionDetailDTO;
import team.univ.magic_conch.question.dto.QuestionListDTO;
import team.univ.magic_conch.utils.page.PageRequestDTO;
import team.univ.magic_conch.utils.page.PageResultDTO;

import java.util.List;
import java.util.Optional;

public interface QuestionService {

    public List<BundleDropBoxDTO> question(String name);
    public void questionForm(Question question);
    public QuestionDetailDTO questionDetail(Long questionNo);
    public PageResultDTO<QuestionListDTO, Question> questionAll(PageRequestDTO pageRequestDTO);
    public PageResultDTO<QuestionListDTO, Question> questionAllByUsername(String username, PageRequestDTO pageRequestDTO);
    public PageResultDTO<QuestionListDTO, Question> questionAllByTitle(String title, PageRequestDTO pageRequestDTO);

}
