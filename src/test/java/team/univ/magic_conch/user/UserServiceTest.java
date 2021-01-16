package team.univ.magic_conch.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    // 회원가입
    @Test
    public void 회원가입() throws Exception {
        // given
        String username = "tester1";
        String password = "seun1234";
        String name = "전세운";

        // when
        User newUser = userService.join(username, password, name);

        // then
        assertEquals(username, newUser.getUsername());
        assertTrue(passwordEncoder.matches(password, newUser.getPassword()));
        assertEquals(name, newUser.getName());
    }

    // 회원가입
    @Test
    public void 중복_아이디_예외() throws Exception {
        // given
        String username1 = "tester1";
        String password1 = "seun1234";
        String name1 = "전세운";

        String username2 = "tester1";
        String password2 = "test1234";
        String name2 = "김철수";

        // when
        userService.join(username1, password1, name1);

        // then
        assertThrows(Exception.class,
                () -> userService.join(username2, password2, name2));
    }

    @Test
    public void ID_중복확인() throws Exception {
        // given
        String username1 = "tester1";
        String password1 = "seun1234";
        String name1 = "전세운";

        String username2 = "tester2";
        userService.join(username1, password1, name1);

        // when
        boolean usernameDuplicate1 = userService.isUsernameDuplicate(username1);
        boolean usernameDuplicate2 = userService.isUsernameDuplicate(username2);

        // then
        assertTrue(usernameDuplicate1);
        assertFalse(usernameDuplicate2);
    }
}