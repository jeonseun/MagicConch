package team.univ.magic_conch.follow;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import team.univ.magic_conch.auth.PrincipalDetails;
import org.springframework.web.bind.annotation.*;
import team.univ.magic_conch.user.User;
import team.univ.magic_conch.user.UserRepository;
import team.univ.magic_conch.user.UserService;
import team.univ.magic_conch.user.dto.UserSimpleDTO;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final UserRepository userRepository;
    private final UserService userService;

    /**
     * 팔로우
     *
     * @param username
     * @param principalDetails
     */
    @PostMapping("follow")
    @ResponseBody
    public void createFollow(@RequestParam(value = "user") String username, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        userRepository.findByUsername(username)
                .ifPresent(user -> followService.addFollow(user, principalDetails.getUser()));
    }

    /**
     * 언팔로우
     *
     * @param username
     * @param principalDetails
     */
    @DeleteMapping("follow")
    @ResponseBody
    public void deleteFollow(@RequestParam(value = "user") String username, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        userRepository.findByUsername(username)
                .ifPresent(user -> followService.deleteFollow(user, principalDetails.getUser()));
    }

    /**
     * 내가 팔로우한 친구 목록
     *
     * @param principalDetails
     * @return 팔로우 친구 목록
     */
    @GetMapping("api/v1/follow/me")
    @ResponseBody
    public List<UserSimpleDTO> followListByUserFrom(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return followService.findAllByUserFrom(principalDetails.getUser());
    }

    /**
     * 나를 팔로우한 친구 목록
     *
     * @param principalDetails
     * @return 팔로워 친구 목록
     */
    @GetMapping("api/v1/follow/you")
    @ResponseBody
    public List<UserSimpleDTO> followListByUserTo(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return followService.findAllByUserTo(principalDetails.getUser());
    }

    // 친구목록 화면구성
    @GetMapping("/friend/overview")
    public String overview(Model model, @RequestParam String username) {
        User findUser = userService.getUser(username);
        // 나를 팔로우한 사람 목록
        model.addAttribute("followers", followService.findAllByUserFrom(findUser));
        // 내가 팔로우한 사람 목록
        model.addAttribute("followings", followService.findAllByUserTo(findUser));
        return "friend/overview";
    }
}
