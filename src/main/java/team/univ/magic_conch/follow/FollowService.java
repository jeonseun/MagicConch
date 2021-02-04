package team.univ.magic_conch.follow;

import team.univ.magic_conch.user.User;
import team.univ.magic_conch.user.dto.SimpleUserDTO;

import java.util.List;

public interface FollowService {

    /**
     * 팔로우
     * @param userFrom
     * @param userTo
     */
    public Follow addFollow(User userFrom, User userTo);

    /**
     * 언팔로우
     * @param userFrom
     * @param userTo
     */
    public void deleteFollow(User userFrom, User userTo);

    /**
     * 내가 팔로우 한 사람들 목록
     * @param userFrom
     * @return List<SimpleUserDTO>
     */
    public List<SimpleUserDTO> findAllByUserFrom(User userFrom);

    /**
     * 나를 팔로우 한 사람들 목록
     * @param userTo
     * @return List<SimpleUserDTO>
     */
    public List<SimpleUserDTO> findAllByUserTo(User userTo);

}
