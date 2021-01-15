package team.univ.magic_conch.user;

import lombok.Getter;

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

    private String username;
    private String password;
    private UserRole role;
    private String name;
    private String profileImg;
    private LocalDate createdDate;
}
