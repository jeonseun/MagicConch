package team.univ.magic_conch.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void 회원_저장() throws Exception {
        // given
        String username1 = "username1";
        String password1 = "password1";
        String name1 = "name1";

        // when
        User savedUser = userRepository.save(User.builder()
                .username(username1)
                .password(password1)
                .name(name1)
                .build());

        Optional<User> findUser = userRepository.findById(savedUser.getId());

        // then
        assertEquals(savedUser, findUser.get());
        assertEquals(username1, findUser.get().getUsername());
        assertEquals(password1, findUser.get().getPassword());
        assertEquals(name1, findUser.get().getName());
    }
    
    // 이겨서 말하는 회원 ID는 엔티티 식별자가 아니고 로그인할 때 사용하는 username 임
    @Test
    public void 회원_ID로_회원검색() throws Exception {
        // given
        String username1 = "username1";
        String password1 = "password1";
        String name1 = "name1";

        String username2 = "username2";
        String password2 = "password2";
        String name2 = "name2";

        User savedUser1 = userRepository.save(User.builder()
                .username(username1)
                .password(password1)
                .name(name1)
                .build());

        User savedUser2 = userRepository.save(User.builder()
                .username(username2)
                .password(password2)
                .name(name2)
                .build());

        // when
        Optional<User> findUser1 = userRepository.findByUsername(username1);
        Optional<User> findUser2 = userRepository.findByUsername(username2);

        // then
        assertEquals(savedUser1, findUser1.get());
        assertEquals(savedUser2, findUser2.get());
        assertEquals(savedUser1.getUsername(), findUser1.get().getUsername());
        assertEquals(savedUser2.getUsername(), findUser2.get().getUsername());
    }
}