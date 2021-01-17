package team.univ.magic_conch.question;

import team.univ.magic_conch.question.dto.QuestionDetailDTO;
import team.univ.magic_conch.question.dto.QuestionListDTO;
import team.univ.magic_conch.utils.page.PageRequestDTO;
import team.univ.magic_conch.utils.page.PageResultDTO;

import java.util.List;
import java.util.Optional;

public interface QuestionService {

    public void question();
    public void createQuestion(Question question);
    public QuestionDetailDTO questionDetail(Long questionNo);
    public PageResultDTO questionAll(PageRequestDTO pageRequestDTO);
    public PageResultDTO questionAllByUsername(String username, PageRequestDTO pageRequestDTO);
    public PageResultDTO questionAllByTitle(String title, PageRequestDTO pageRequestDTO);

    default QuestionListDTO entityToQuestionListDto(Question question){
        return QuestionListDTO.builder()
                .questionId(question.getId())
                .title(question.getTitle())
                .view(question.getView())
                .createTime(question.getCreateTime())
                .username(question.getUser().getUsername())
                /*.tagName(question.getTag().getName())*/
                .build();
    }

    default QuestionDetailDTO entityToQuestionDetailDto(Question question){
        return QuestionDetailDTO.builder()
                .questionId(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .view(question.getView())
                .createTime(question.getCreateTime())
                .lastModifyTime(question.getLastModifyTime())
                .username(question.getUser().getUsername())
                /*.tagName()
                .bundleId()*/
                .build();
    }

}
