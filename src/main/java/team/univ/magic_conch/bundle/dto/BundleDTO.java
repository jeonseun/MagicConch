package team.univ.magic_conch.bundle.dto;

import lombok.*;

import java.time.LocalDate;

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
    public static class MyBundleDTO {
        private String bundleName;
        private String tagName;
        private String tagColor;
        private Integer questionCount;
        private LocalDate createTime;
        private String visibility;
    }
}
