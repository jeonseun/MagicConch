package team.univ.magic_conch.bundle;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import team.univ.magic_conch.bundle.dto.BundleDropBoxDTO;
import team.univ.magic_conch.bundle.dto.BundleHeaderDTO;
import team.univ.magic_conch.bundle.dto.BundlePreviewDTO;
import team.univ.magic_conch.question.Question;
import team.univ.magic_conch.tag.Tag;
import team.univ.magic_conch.user.User;
import team.univ.magic_conch.visibility.Visibility;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ToString
@Entity
@Getter
public class Bundle {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bundle_id")
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Visibility visibility;

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
    public Bundle(String name, Visibility visibility, User user, Tag tag) {
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

    public BundleHeaderDTO toBundleHeaderDTO() {
        return BundleHeaderDTO.builder()
                .bundleName(getName())
                .tagName(getTag().getName())
                .tagColor(getTag().getColor())
                .createdDate(getCreateDate())
                .visibility(getVisibility().toString())
                .build();
    }

    public BundlePreviewDTO entityToPreviewDTO() {
        return BundlePreviewDTO.builder()
                .bundleId(getId())
                .bundleName(getName())
                .tagName(getTag().getName())
                .tagColor(getTag().getColor())
                .visibility(getVisibility().toString())
                .build();
    }
}
