package team.univ.magic_conch.bundle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BundleDropBoxDTO {

    private Long bundleId;
    private String bundleName;
    private String tagName;
    private String tagColor;

}
