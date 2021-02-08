package team.univ.magic_conch.bundle.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BundlePreviewDTO {
    private Long bundleId;
    private String bundleName;
    private String tagName;
    private String tagColor;
    private String visibility;

    @Builder
    public BundlePreviewDTO(Long bundleId, String bundleName, String tagName, String tagColor, String visibility) {
        this.bundleId = bundleId;
        this.bundleName = bundleName;
        this.tagName = tagName;
        this.tagColor = tagColor;
        this.visibility = visibility;
    }
}
