package team.univ.magic_conch.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import team.univ.magic_conch.auth.PrincipalDetails;
import team.univ.magic_conch.bundle.Bundle;
import team.univ.magic_conch.bundle.BundleRepository;
import team.univ.magic_conch.bundle.dto.BundlePreviewDTO;
import team.univ.magic_conch.follow.FollowService;
import team.univ.magic_conch.user.dto.UserProfileDTO;
import team.univ.magic_conch.user.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class UserController {


    private final UserRepository userRepository;
    private final BundleRepository bundleRepository;
    private final FollowService followService;
    // !! sample url information
    // url : http://{host_ip}:{port}/user/{page-name}?username={target-username}

    // user info overview page
    @GetMapping("/user/info")
    public String infoPage(@RequestParam(required = false) String username,
                           @AuthenticationPrincipal PrincipalDetails principalDetails,
                           Model model) {
        // Request from login user
        if(principalDetails != null) {
            User loginUser = principalDetails.getUser();
            // own user info page
            if (username == null || username.equals(principalDetails.getUsername())) {
                // convert user data -> dto
                UserProfileDTO userDTO = loginUser.toUserProfileDTO();

                // data push in model
                model.addAttribute("user", userDTO);
                model.addAttribute("isMine", true);
            }
            // another user info page
            else {
                // get user data
                User findUser = userRepository.findByUsername(username)
                        .orElseThrow(UserNotFoundException::new);
                // convert user data -> dto
                UserProfileDTO userDTO = findUser.toUserProfileDTO();

                // get follow or not
                boolean followed = followService.isFollowed(findUser, loginUser);

                // data push in model
                model.addAttribute("user", userDTO);
                model.addAttribute("followed", followed);
                model.addAttribute("isMine", false);
            }

            // get bundle data
            List<BundlePreviewDTO> bundleDTOs = bundleRepository.findAllByUserUsername(loginUser.getUsername())
                    .stream()
                    .map(Bundle::toBundlePreviewDTO)
                    .collect(Collectors.toList());
            model.addAttribute("bundles", bundleDTOs);
        }
        // Request from non-login user
        else {
            // get user data
            User findUser = userRepository.findByUsername(username)
                    .orElseThrow(UserNotFoundException::new);

            // convert user data -> dto
            UserProfileDTO userDTO = findUser.toUserProfileDTO();
            model.addAttribute("user", userDTO);

            // get bundle data
            List<BundlePreviewDTO> bundleDTOs = bundleRepository.findAllByUserUsername(findUser.getUsername())
                    .stream()
                    .map(Bundle::toBundlePreviewDTO)
                    .collect(Collectors.toList());
            model.addAttribute("bundles", bundleDTOs);
        }
        return "user/info";
    }

    // user info bundle overview page
    @GetMapping("/user/bundle")
    public String bundleOverview(@RequestParam(required = false) String username) {

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

//    @PutMapping("/profile/image")
//    @ResponseBody
//    public ResponseEntity changeProfileImage(MultipartFile profileImage,
//                                             @AuthenticationPrincipal PrincipalDetails principalDetails) {
//        User currentUser = principalDetails.getUser();
//        String profileImagePath = userService.changeProfileImage(profileImage, currentUser.getUsername());
//        if (profileImagePath == null) {
//            return ResponseEntity.badRequest().body("프로필 사진 변경중 오류가 발행했습니다.");
//        }
//        // 시큐리티 컨텍스트 내부의 사용자 정보에도 반영되록함
//        currentUser.changeProfileImage(profileImagePath);
//        return ResponseEntity.ok().body(profileImagePath);
//    }

    @ExceptionHandler({UserNotFoundException.class})
    public String handleException(){
        return "error/404";
    }
}
