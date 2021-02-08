package team.univ.magic_conch.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.univ.magic_conch.user.dto.SimpleUserDTO;
import team.univ.magic_conch.user.dto.UserProfileDTO;

import javax.annotation.processing.Generated;
import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "user_password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRole userRole;

    @Column(name = "user_real_name")
    private String name;

    @Column(name = "user_created_date")
    private LocalDate createdDate;

    @Column(name = "user_profile_image")
    private String profileImg;

    @Builder
    public User(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
        userRole = UserRole.ROLE_USER;
        createdDate = LocalDate.now();
        profileImg = "/image/default_profile_image.png";
    }

    protected User() { }

    /**
     * 프로필 이미지 변경
     * @param profileImg 신규 프로필 이미지 경로
     */
    public void changeProfileImage(String profileImg) {
        this.profileImg = profileImg;
    }

    public SimpleUserDTO entityToSimpleUserDto(){
        return SimpleUserDTO.builder()
                .username(getUsername())
                .profileImg(getProfileImg())
                .build();
    }

    public UserProfileDTO toUserProfileDTO() {
        return UserProfileDTO.builder()
                .username(getUsername())
                .name(getName())
                .profileImage(getProfileImg())
                .build();
    }
}
