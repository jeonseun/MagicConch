package team.univ.magic_conch.team;

import team.univ.magic_conch.bundle.Bundle;
import team.univ.magic_conch.user.User;

public interface TeamService {

    /**
     * 신규 팀 생성 (번들 생성시 자동으로 생성됨)
     * @param bundle
     * @param user
     * @return 생성된 Team Entity
     */
    Team createTeam(Bundle bundle, User user);

    /**
     * 번들 ID로 Team Entity 검색
     * @param bundleId
     * @return Team Entity
     */
    Team getByBundleId(Long bundleId);

}
