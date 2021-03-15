package team.univ.magic_conch.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.univ.magic_conch.auth.PrincipalDetails;
import team.univ.magic_conch.auth.dto.JoinDataDTO;
import team.univ.magic_conch.follow.FollowService;
import team.univ.magic_conch.user.dto.UserProfileDTO;
import team.univ.magic_conch.user.dto.UserSimpleDTO;
import team.univ.magic_conch.user.exception.UserNotFoundException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {


    private final UserRepository userRepository;
    private final FollowService followService;
    private final UserService userService;

    // user info overview 페이지
    @GetMapping("/overview")
    public String overview(@RequestParam(required = false) String username,
                           @AuthenticationPrincipal PrincipalDetails principalDetails,
                           Model model) {
        // user info 페이지에 필요한 정보 가져오기
        // user 정보
        User findUser = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
        // user 정보 profile dto 변환 (팔로우 여부 미포함)
        UserProfileDTO userDTO = findUser.entityToUserProfileDTO();

        // profile dto 팔로우 여부 등록
        if (principalDetails != null) {
            boolean followed = followService.isFollowed(findUser, principalDetails.getUser());
            userDTO.setFollowed(followed);
        }
        model.addAttribute("profileDTO", userDTO);

        model.addAttribute("info", findUser.entityToUserInfoDTO());
        return "user/overview";
    }

    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<List<UserSimpleDTO>> searchUser(@RequestParam String username) {
        List<User> users = userService.searchUserByUsername(username);
        if (users.size() == 0) {
            return ResponseEntity.noContent().build();
        }
        List<UserSimpleDTO> userSimpleDTOS = users.stream()
                .map(User::entityToSimpleUserDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(userSimpleDTOS);
    }

    @PostMapping("/info")
    public String updateInfo(@RequestParam String aboutMe,
                             @RequestParam String interests,
                             @RequestParam String career,
                             MultipartFile profileImage,
                             @AuthenticationPrincipal PrincipalDetails principalDetails) {
        User currentUser = principalDetails.getUser();
        currentUser.updateInfo(aboutMe, interests, career);

        if (!profileImage.isEmpty()) {
            String profileImagePath = userService.changeProfileImage(profileImage, currentUser.getUsername());
            // 시큐리티 컨텍스트 내부의 사용자 정보에도 반영되록함
            currentUser.changeProfileImage(profileImagePath);
        }

        userRepository.save(currentUser);
        return "redirect:/user/overview?username=" + currentUser.getUsername();
    }


    @GetMapping("/profile/manage")
    public String profileUpdatePage() {
        return "user/manage";
    }
}
