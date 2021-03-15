package team.univ.magic_conch.user;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    /**
     * 유저 이름으로 해당 유저 검색
     * @param username
     * @return 검색된 User Entity
     */
    User getUser(String username);

    /**
     * 새로운 회원을 등록
     *
     * @param username 신규 회원 ID
     * @param password 신규 회원 비밀번호
     * @param name     신규 회원이 사용할 이름
     * @return 등록된 User Entity
     */
    User join(String username, String password, String name);

    /**
     * 회원가입에 쓰일 유저네임(로그인 ID) 중복여부 확인
     *
     * @param username 신규 회원 ID
     * @return 중복되면 true, 중복되지 않으면 false
     */
    boolean isUsernameDuplicate(String username);

    /**
     * 프로필 이미지 변경
     * @param multipartFile 전송받은 멀티파트 파일
     * @param username 현재 접속중인 사용자 ID (프로필 변경 대상)
     * @return 변경된 프로필 이미지 접근 경로
     */
    String changeProfileImage(MultipartFile multipartFile, String username);

    /**
     * 사용자 id로 사용자 검색
     * @param username
     * @return User List
     */
    List<User> searchUserByUsername(String username);
}
