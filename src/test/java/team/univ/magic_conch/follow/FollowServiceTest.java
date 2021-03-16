package team.univ.magic_conch.follow;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import team.univ.magic_conch.follow.dto.BestFollowerDTO;
import team.univ.magic_conch.user.User;
import team.univ.magic_conch.user.UserRepository;
import team.univ.magic_conch.user.dto.UserSimpleDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Transactional
class FollowServiceTest {
    
    @Autowired
    private FollowService followService;
    @Autowired
    private FollowRepository followRepository;
    @Autowired
    private UserRepository userRepository;
    
    @Test
    public void 팔로우() throws Exception {
        //given
        User user = User.builder().build();
        User user2 = User.builder().build();
        userRepository.save(user);
        userRepository.save(user2);
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
        userRepository.save(user);
        userRepository.save(user2);
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
        userRepository.save(user);
        for (int i = 0; i < 10; i++) {
            User user2 = User.builder().username("test" + i).build();
            userRepository.save(user2);
            followRepository.save(new Follow(user, user2));
            followRepository.save(new Follow(user2, user));
        }
        //when
        List<UserSimpleDTO> resultByUserFrom = followService.findAllByUserFrom(user);
        List<UserSimpleDTO> resultByUserTo = followService.findAllByUserTo(user);
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

    @Test
    public void 팔로우베스트5() throws Exception {
        //given
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            User user = User.builder().username("test" + i).build();
            userRepository.save(user);
            users.add(user);
        }
        for (int i = 0; i < users.size() - 1; i++) {
            for (int j = i + 1; j < users.size(); j++) {
                followRepository.save(new Follow(users.get(i), users.get(j)));
            }
        }
        //when
        List<BestFollowerDTO> result = followService.findBestFollower();

        //then
        Assertions.assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("profileImg")
                .isEqualTo(
                        Arrays.asList(
                        new BestFollowerDTO("test4",null, 4L),
                        new BestFollowerDTO("test3", null, 3L),
                        new BestFollowerDTO("test2", null, 2L),
                        new BestFollowerDTO("test1", null, 1L)
                ));
    }

}