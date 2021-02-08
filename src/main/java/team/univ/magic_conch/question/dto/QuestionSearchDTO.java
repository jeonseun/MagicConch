package team.univ.magic_conch.question.dto;

import lombok.Getter;

@Getter
public class QuestionSearchDTO {

    Integer pageNo;
    String username;
    String title;
    String tagName;

    public QuestionSearchDTO(Integer page, String user, String title, String tag) {
        this.pageNo = page == null ? 1 : page;
        this.username = user;
        this.title = title;
        this.tagName = tag;
    }
}
