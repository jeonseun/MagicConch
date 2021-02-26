package team.univ.magic_conch.visibility;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.univ.magic_conch.follow.FollowService;
import team.univ.magic_conch.user.User;

@RequiredArgsConstructor
@Service
public class UserRelationServiceImpl implements UserRelationService {

    private final FollowService followService;

    @Override
    public UserRelation checkRelation(User requester, User target) {
        if (requester == target) {
            // 자기자신
            return UserRelation.MY_SELF;
        }
        // 요청자가 대상을 팔로우 하는지
        boolean toTarget = followService.isFollowed(requester, target);
        // 대상이 요청자를 팔로우 하는지
        boolean toRequester = followService.isFollowed(target, requester);
        if (toTarget && toRequester) {
            // 맞팔인 경우 친구로 간주
            return UserRelation.FRIEND;
        }
        // 한쪽만 팔로우 하거나 서로 팔로우 하지 않는 경우 아무 관계 없음
        return UserRelation.NO_RELATIONSHIP;
    }
}
