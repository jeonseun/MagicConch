package team.univ.magic_conch.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * 새로운 회원을 등록
     *
     * @param username 신규 회원 ID
     * @param password 신규 회원 비밀번호
     * @param name     신규 회원이 사용할 이름
     * @return 등록된 User Entity
     */
    public User join(String username, String password, String name) {
        User newUser = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .name(name)
                .build();
        userRepository.save(newUser);
        return newUser;
    }

    /**
     * 회원가입에 쓰일 유저네임(로그인 ID) 중복여부 확인
     *
     * @param username 신규 회원 ID
     * @return 중복되면 true, 중복되지 않으면 false
     */
    public boolean isUsernameDuplicate(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            return true;
        }
        return false;
    }
}
