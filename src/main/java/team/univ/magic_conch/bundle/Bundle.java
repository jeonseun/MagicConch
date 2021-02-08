package team.univ.magic_conch.bundle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.univ.magic_conch.bundle.dto.BundleDTO;
import team.univ.magic_conch.bundle.dto.BundleDropBoxDTO;
import team.univ.magic_conch.bundle.dto.BundlePreviewDTO;
import team.univ.magic_conch.question.Question;
import team.univ.magic_conch.tag.Tag;
import team.univ.magic_conch.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Bundle {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bundle_id")
    private Long id;

    private String name;
    private String visibility;
    private LocalDate createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @OneToMany(mappedBy = "bundle")
    private List<Question> questions = new ArrayList<>();

    @Builder
    public Bundle(String name, String visibility, User user, Tag tag) {
        this.name = name;
        this.visibility = visibility;
        this.user = user;
        this.tag = tag;
        this.createDate = LocalDate.now();
        this.questions = new ArrayList<>();
    }

    protected Bundle() {

    }

    public void addQuestion(Question question){
        this.questions.add(question);
        if (question.getBundle() != this) {
            question.changeBundle(this);
        }
    }

    public BundleDropBoxDTO entityToBundleDropBoxDTO(){
        return BundleDropBoxDTO.builder()
                .bundleId(getId())
                .bundleName(getName())
                .tagName(getTag().getName())
                .tagColor(getTag().getColor())
                .build();
    }

    public BundlePreviewDTO toBundlePreviewDTO() {
        return BundlePreviewDTO.builder()
                .bundleId(getId())
                .bundleName(getName())
                .tagName(getTag().getName())
                .tagColor(getTag().getColor())
                .visibility(getVisibility())
                .build();
    }

}
