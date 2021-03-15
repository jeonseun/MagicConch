package team.univ.magic_conch.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import team.univ.magic_conch.user.exception.UserNotFoundException;
import team.univ.magic_conch.utils.file.StorageService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final StorageService storageService;

    // 이미지 파일 저장 경로
    @Value("${custom.file.profile-location}")
    private String location;

    @Override
    public User getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User join(String username, String password, String name) {
        User newUser = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .name(name)
                .build();
        userRepository.save(newUser);
        return newUser;
    }


    @Override
    public boolean isUsernameDuplicate(String username) {
        Optional<User> findUser = userRepository.findByUsername(username);
        return findUser.isPresent();
    }

    @Override
    public String changeProfileImage(MultipartFile imageFile, String username) {
        try {
            String path = storageService.save(imageFile.getBytes(), imageFile.getOriginalFilename(), location);
            userRepository.findByUsername(username)
                    .ifPresent((user) -> user.changeProfileImage(path));
            return path;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<User> searchUserByUsername(String username) {
        return userRepository.findAllByUsernameContaining(username);
    }

}
