package team.univ.magic_conch.follow;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import team.univ.magic_conch.user.User;
import team.univ.magic_conch.user.dto.SimpleUserDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class FollowServiceTest {
    
    @Autowired
    private FollowService followService;
    @Autowired
    private FollowRepository followRepository;
    
    @Test
    public void 팔로우() throws Exception {
        //given
        User user = User.builder().build();
        User user2 = User.builder().build();
        //when
        Follow result = followService.addFollow(user, user2);
        //then
        Assertions.assertThat(result)
                .isEqualTo(followRepository.findAll().get(0));

    }
    
    @Test
    public void 언팔로우() throws Exception {
        //given
        User user = User.builder().build();
        User user2 = User.builder().build();
        //when
        followRepository.save(new Follow(user, user2));
        followService.deleteFollow(user, user2);
        //then
        Assertions.assertThat(followRepository.findAll().size())
                .isEqualTo(0);
    }
    
    @Test
    public void 팔로우조회() throws Exception {
        //given
        User user = User.builder().username("test").build();
        for (int i = 0; i < 10; i++) {
            User user2 = User.builder().username("test" + i).build();
            followRepository.save(new Follow(user, user2));
            followRepository.save(new Follow(user2, user));
        }
        //when
        List<SimpleUserDTO> resultByUserFrom = followService.findAllByUserFrom(user);
        List<SimpleUserDTO> resultByUserTo = followService.findAllByUserTo(user);
        //then
        for (int i = 0; i < 10; i++) {
            Assertions.assertThat(resultByUserFrom.get(i).getUsername())
                    .isEqualTo("test" + i);
        }
        Assertions.assertThat(resultByUserTo)
                .extracting("username")
                .containsOnly("test");
        Assertions.assertThat(resultByUserTo.size())
                .isEqualTo(10);
    }

}