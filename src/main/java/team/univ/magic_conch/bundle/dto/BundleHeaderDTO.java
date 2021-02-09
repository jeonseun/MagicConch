package team.univ.magic_conch.bundle.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class BundleHeaderDTO {
    private String bundleName;
    private String tagName;
    private String tagColor;
    private String visibility;
    private LocalDate createdDate;

    @Builder
    public BundleHeaderDTO(String bundleName, String tagName, String tagColor,String visibility, LocalDate createdDate) {
        this.bundleName = bundleName;
        this.tagName = tagName;
        this.tagColor = tagColor;
        this.visibility = visibility;
        this.createdDate = createdDate;
    }

    protected BundleHeaderDTO() {

    }
}
