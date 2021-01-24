package team.univ.magic_conch.bundle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.univ.magic_conch.bundle.dto.BundleDTO;
import team.univ.magic_conch.bundle.dto.BundleDropBoxDTO;
import team.univ.magic_conch.question.Question;
import team.univ.magic_conch.tag.Tag;
import team.univ.magic_conch.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    @Builder.Default
    private List<Question> questions = new ArrayList<>();

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

    public BundleDTO.MyBundle toMyBundleDTO() {
        return BundleDTO.MyBundle.builder()
                .bundleId(getId())
                .bundleName(getName())
                .createTime(getCreateDate())
                .questionCount(getQuestions().size())
                .tagName(getTag().getName())
                .tagColor(getTag().getColor())
                .visibility(getVisibility())
                .build();
    }
}
