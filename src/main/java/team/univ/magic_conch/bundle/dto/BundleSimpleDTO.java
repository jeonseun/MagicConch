package team.univ.magic_conch.bundle.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

/**
 * 번들 자체에 관한 정보만을 가지는 DTO
 */
@Getter
public class BundleSimpleDTO {

    private Long bundleId;
    private String name;
    private String visibility;
    private LocalDate createdTime;
    private String tagColor;
    private String tagName;

    @Builder
    public BundleSimpleDTO(Long bundleId, String name, String visibility, LocalDate createdTime, String tagColor, String tagName) {
        this.bundleId = bundleId;
        this.name = name;
        this.visibility = visibility;
        this.createdTime = createdTime;
        this.tagColor = tagColor;
        this.tagName = tagName;
    }

    protected BundleSimpleDTO() {

    }
}
