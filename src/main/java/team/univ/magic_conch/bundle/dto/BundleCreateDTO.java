package team.univ.magic_conch.bundle.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class BundleCreateDTO {

    private String bundleName;
    private String visibility;
    private String tagName;

}
