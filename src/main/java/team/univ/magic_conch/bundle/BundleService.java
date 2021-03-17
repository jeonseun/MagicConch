package team.univ.magic_conch.bundle;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import team.univ.magic_conch.tag.Tag;
import team.univ.magic_conch.team.Team;
import team.univ.magic_conch.user.User;

import java.util.List;
import java.util.Optional;

public interface BundleService {

    /**
     * 번들 식별자로 번들 검색
     * @param id
     * @return Optional<Bundle>
     */
     Optional<Bundle> findById(Long id);

    /**
     * 신규 번들 생성
     * @param name
     * @param tag
     * @param user
     * @param accessLevel
     */
    Bundle createBundle(String name, Tag tag, User user, AccessLevel accessLevel);

    /**
     * username 으로 해당 사용자가 생성한 번들 목록 반환
     * @param username 번들 소유자
     * @param pageable 페이징 처리용
     * @return 번들 페이징
     */
    Page<Bundle> getBundleByUsername(String username, Pageable pageable);

    /**
     * 번들 이름으로 번들 검색
     * @param bundleName
     * @return Bundle Entity List
     */
    List<Bundle> searchBundle(String bundleName);


    /**
     * 해당 팀에 연결된 번들 리스트 반환
     * @param team
     * @return Bundle Entity List
     */
    List<Bundle> getLinkedTeam(Team team);

    /**
     * 자신이 생성한 모든 번들 가져오기
     * @param username
     * @return Bundle Entity List
     */
    List<Bundle> getMyBundle(String username);
}
