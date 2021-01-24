package team.univ.magic_conch.bundle.dto;

import lombok.*;
import team.univ.magic_conch.question.Question;

import java.time.LocalDate;
import java.util.List;

public class BundleDTO {

    @Setter
    @Getter
    public static class BundleForm {
        private String bundleName;
        private String visibility;
        private String tagName;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Setter
    @Getter
    public static class MyBundle {
        private Long bundleId;
        private String bundleName;
        private String tagName;
        private String tagColor;
        private Integer questionCount;
        private LocalDate createTime;
        private String visibility;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class BundleDetails {
        private String bundleName;
        private String tagName;
        private String tagColor;
        private LocalDate createDate;
        private List<Question> questions;
    }
}
