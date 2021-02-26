package team.univ.magic_conch.visibility;

import team.univ.magic_conch.user.User;

public interface UserRelationService {

    /**
     * 요청자와 요청자가 열람을 희망하는 사람간의 관계 식별
     * (자기자신 또는 팔로우 정보를 기반으로 친구, 무관계 식별)
     * @param requester
     * @param target
     * @return UserRelation Enum
     */
    UserRelation checkRelation(User requester, User target);
}
