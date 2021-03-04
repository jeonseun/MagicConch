package team.univ.magic_conch.bundle.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

/**
 * 번들 자체에 관한 정보만을 가지는 DTO (번들의 이름, 생성일과 접근 레벨, 태그 관련 정보 포함)
 */
@Getter
public class BundleInfoDTO {

    private Long bundleId;
    private String name;
    private String visibility;
    private LocalDate createdTime;
    private String tagColor;
    private String tagName;
    private String tagImage;

    @Builder
    public BundleInfoDTO(Long bundleId, String name, String visibility, LocalDate createdTime, String tagColor, String tagName, String tagImage) {
        this.bundleId = bundleId;
        this.name = name;
        this.visibility = visibility;
        this.createdTime = createdTime;
        this.tagColor = tagColor;
        this.tagName = tagName;
        this.tagImage = tagImage;
    }

    protected BundleInfoDTO() {

    }
}
