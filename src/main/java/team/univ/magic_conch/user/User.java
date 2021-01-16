package team.univ.magic_conch.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.processing.Generated;
import javax.persistence.*;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserRole role = UserRole.ROLE_USER;
    private String name;
    private String profileImg;

    @Builder.Default
    private LocalDate createDate = LocalDate.now();
}
