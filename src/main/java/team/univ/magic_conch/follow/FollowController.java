package team.univ.magic_conch.follow;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import team.univ.magic_conch.config.auth.PrincipalDetails;
import team.univ.magic_conch.user.UserRepository;
import team.univ.magic_conch.user.dto.SimpleUserDTO;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final UserRepository userRepository;

    @PostMapping("follow")
    @ResponseBody
    public void createFollow(@RequestParam(value = "user") String username, @AuthenticationPrincipal PrincipalDetails principalDetails){
        userRepository.findByUsername(username)
                .ifPresent(user -> followService.addFollow(user, principalDetails.getUser()));
    }

    @DeleteMapping("follow")
    @ResponseBody
    public void deleteFollow(@RequestParam(value = "user") String username, @AuthenticationPrincipal PrincipalDetails principalDetails){
        userRepository.findByUsername(username)
                .ifPresent(user -> followService.deleteFollow(user, principalDetails.getUser()));
    }

    @GetMapping("api/v1/follow/me")
    @ResponseBody
    public List<SimpleUserDTO> followListByUserFrom(@AuthenticationPrincipal PrincipalDetails principalDetails){
        return followService.findAllByUserFrom(principalDetails.getUser());
    }

    @GetMapping("api/v1/follow/you")
    @ResponseBody
    public List<SimpleUserDTO> followListByUserTo(@AuthenticationPrincipal PrincipalDetails principalDetails){
        return followService.findAllByUserTo(principalDetails.getUser());
    }

}
