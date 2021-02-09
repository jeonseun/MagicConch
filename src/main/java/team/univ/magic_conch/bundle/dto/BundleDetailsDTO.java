package team.univ.magic_conch.bundle.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class BundleDetailsDTO {

    private Long bundleId;
    private String bundleName;
    private String visibility;
    private String tagName;
    private String tagColor;
    private LocalDate createdDate;
    private int questionCount;

    @Builder
    public BundleDetailsDTO(Long bundleId, String bundleName, String visibility, String tagName, String tagColor, LocalDate createdDate, int questionCount) {
        this.bundleId = bundleId;
        this.bundleName = bundleName;
        this.visibility = visibility;
        this.tagName = tagName;
        this.tagColor = tagColor;
        this.createdDate = createdDate;
        this.questionCount = questionCount;
    }

    protected BundleDetailsDTO() {

    }
}
