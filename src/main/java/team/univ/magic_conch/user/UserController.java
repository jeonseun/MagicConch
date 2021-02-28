package team.univ.magic_conch.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.univ.magic_conch.auth.PrincipalDetails;
import team.univ.magic_conch.bundle.Bundle;
import team.univ.magic_conch.bundle.BundleRepository;
import team.univ.magic_conch.bundle.dto.BundleDetailsDTO;
import team.univ.magic_conch.follow.FollowService;
import team.univ.magic_conch.question.QuestionService;
import team.univ.magic_conch.user.dto.UserProfileDTO;
import team.univ.magic_conch.user.exception.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {


    private final UserRepository userRepository;
    private final BundleRepository bundleRepository;
    private final FollowService followService;
    private final UserService userService;
    private final QuestionService questionService;

    // 마이페이지 관련 URL 예시
    // url : http://{host_ip}:{port}/user/{페이지 이름}?username={대상 사용자}

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
        return "user/overview";
    }

    @PutMapping("/profile/image")
    @ResponseBody
    public ResponseEntity changeProfileImage(MultipartFile profileImage,
                                             @AuthenticationPrincipal PrincipalDetails principalDetails) {
        User currentUser = principalDetails.getUser();
        String profileImagePath = userService.changeProfileImage(profileImage, currentUser.getUsername());
        if (profileImagePath == null) {
            return ResponseEntity.badRequest().body("프로필 사진 변경중 오류가 발행했습니다.");
        }
        // 시큐리티 컨텍스트 내부의 사용자 정보에도 반영되록함
        currentUser.changeProfileImage(profileImagePath);
        return ResponseEntity.ok().body(profileImagePath);
    }

    @ExceptionHandler({UserNotFoundException.class})
    public String handleException() {
        return "error/404";
    }

}
