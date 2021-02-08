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

    /**
     * 팔로우 중인지 확인
     * @param userFrom 본인
     * @param userTo   대상
     * @return true : 팔로우 중, false : 팔로우 중 아님
     */
    boolean isFollowed(User userFrom, User userTo);
}
