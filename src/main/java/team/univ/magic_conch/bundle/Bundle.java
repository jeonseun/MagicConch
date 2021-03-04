package team.univ.magic_conch.bundle;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import team.univ.magic_conch.bundle.dto.BundleDropBoxDTO;
import team.univ.magic_conch.bundle.dto.BundleInfoDTO;
import team.univ.magic_conch.question.Question;
import team.univ.magic_conch.tag.Tag;
import team.univ.magic_conch.user.User;
import team.univ.magic_conch.team.AccessLevel;

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
    private AccessLevel accessLevel;

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
    public Bundle(String name, AccessLevel accessLevel, User user, Tag tag) {
        this.name = name;
        this.accessLevel = accessLevel;
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

    public BundleInfoDTO entityToInfoDTO() {
        return BundleInfoDTO.builder()
                .bundleId(getId())
                .createdTime(getCreateDate())
                .name(getName())
                .tagName(getTag().getName())
                .tagColor(getTag().getColor())
                .accessLevel(getAccessLevel().toString())
                .tagImage(getTag().getImage())
                .build();
    }
}
