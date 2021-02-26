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
import team.univ.magic_conch.bundle.dto.BundlePreviewDTO;
import team.univ.magic_conch.follow.FollowService;
import team.univ.magic_conch.question.QuestionService;
import team.univ.magic_conch.user.dto.UserProfileDTO;
import team.univ.magic_conch.user.exception.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class UserController {


    private final UserRepository userRepository;
    private final BundleRepository bundleRepository;
    private final FollowService followService;
    private final UserService userService;
    private final QuestionService questionService;

    // !! sample url information
    // url : http://{host_ip}:{port}/user/{page-name}?username={target-username}

    // user info overview page
    @GetMapping("/user/info")
    public String infoPage(@RequestParam(required = false) String username,
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
        return "user/info";
    }

    // user info bundle overview page
    @GetMapping("/user/bundle")
    public String bundleOverview(@RequestParam(required = false) String username,
                                 @AuthenticationPrincipal PrincipalDetails principalDetails,
                                 Model model) {
        List<BundleDetailsDTO> bundles = new ArrayList<>();
        // 번들 모아보기 페이지에 필요한 정보 조회
        List<Bundle> findBundles = bundleRepository.findAllByUserUsername(username);
        for (Bundle findBundle : findBundles) {
            BundleDetailsDTO bundleDetailsDTO = BundleDetailsDTO.builder()
                    .bundleId(findBundle.getId())
                    .bundleName(findBundle.getName())
                    .visibility(findBundle.getVisibility().toString())
                    .tagName(findBundle.getTag().getName())
                    .tagColor(findBundle.getTag().getColor())
                    .createdDate(findBundle.getCreateDate())
                    .questionCount(questionService.getQuestionCount(findBundle.getId()))
                    .build();
            bundles.add(bundleDetailsDTO);
        }
        model.addAttribute("bundles", bundles);

        return "user/bundleOverview";
    }

    // user info question overview page
    @GetMapping("/user/question")
    public String questionOverview(@RequestParam(required = false) String username) {
        return "user/questionOverview";
    }

    // user info friend overview page
    @GetMapping("/user/friend")
    public String friendOverview(@RequestParam(required = false) String username) {
        return "user/friendOverview";
    }

    // user personal setting page
    @GetMapping("/user/setting")
    public String setting() {
        return "user/setting";
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
