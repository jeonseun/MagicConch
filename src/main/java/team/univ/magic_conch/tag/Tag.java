package team.univ.magic_conch.tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.univ.magic_conch.tag.dto.TagDTO;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Tag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @Column(unique = true)
    private String name;
    private LocalDate createDate;
    private String color;

    public TagDTO entityToTagDto(){
        return TagDTO.builder()
                .name(getName())
                .color(getColor())
                .build();
    }
}
