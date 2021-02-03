package team.univ.magic_conch.follow;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public void createFollow(@RequestParam(value = "user") String username, @AuthenticationPrincipal PrincipalDetails principalDetails){
        userRepository.findByUsername(username)
                .ifPresent(user -> followService.addFollow(user, principalDetails.getUser()));
    }

    @DeleteMapping("follow")
    public void deleteFollow(@RequestParam(value = "user") String username, @AuthenticationPrincipal PrincipalDetails principalDetails){
        userRepository.findByUsername(username)
                .ifPresent(user -> followService.deleteFollow(user, principalDetails.getUser()));
    }

    @GetMapping("follow/me")
    public List<SimpleUserDTO> followListByUserFrom(@AuthenticationPrincipal PrincipalDetails principalDetails){
        return followService.findAllByUserFrom(principalDetails.getUser());
    }

    @GetMapping("follow/you")
    public List<SimpleUserDTO> followListByUserTo(@AuthenticationPrincipal PrincipalDetails principalDetails){
        return followService.findAllByUserTo(principalDetails.getUser());
    }

}
