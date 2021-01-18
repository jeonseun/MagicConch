package team.univ.magic_conch.user;

public interface UserService {

    // 신규 회원 등록
    User join(String username, String password, String name);

    // 로그인 ID 중복확인
    boolean isUsernameDuplicate(String username);
}
