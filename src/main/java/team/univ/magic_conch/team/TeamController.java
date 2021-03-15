package team.univ.magic_conch.team;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import team.univ.magic_conch.auth.PrincipalDetails;
import team.univ.magic_conch.bundle.Bundle;
import team.univ.magic_conch.bundle.BundleService;
import team.univ.magic_conch.bundle.dto.BundleInfoDTO;
import team.univ.magic_conch.bundle.exception.BundleNotFoundException;
import team.univ.magic_conch.team.dto.TeamCreateDTO;
import team.univ.magic_conch.team.dto.TeamInfoDTO;
import team.univ.magic_conch.team.dto.TeamUserInfoDTO;
import team.univ.magic_conch.user.User;
import team.univ.magic_conch.user.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;
    private final BundleService bundleService;
    private final UserService userService;
    private final TeamUserService teamUserService;

    // 팀 오버뷰 페이지 반환
    @GetMapping("/overview")
    public String teamOverview(@RequestParam(required = false) String username,
                               @AuthenticationPrincipal PrincipalDetails principalDetails,
                               Model model) {
        List<TeamInfoDTO> myTeams = teamService.getMyTeam(username)
                .stream()
                .map(Team::entityToTeamInfoDTO)
                .collect(Collectors.toList());
        List<TeamInfoDTO> ownTeams = teamService
                .getOwnTeam(username).stream()
                .map(Team::entityToTeamInfoDTO)
                .collect(Collectors.toList());
        model.addAttribute("myTeams", myTeams);
        model.addAttribute("ownTeams", ownTeams);
        model.addAttribute("curPageUser", username);
        return "team/overview";
    }

    // 팀 생성 폼 반환
    @GetMapping("/createForm")
    public String teamCreateForm(Model model) {
        model.addAttribute("createForm", new TeamCreateDTO());
        return "team/teamCreateForm";
    }

    // 팀 생성 처리 -> 성공 시 팀 오버뷰 페이지로
    @PostMapping
    public String createTeam(@ModelAttribute TeamCreateDTO teamCreateDTO,
                             @AuthenticationPrincipal PrincipalDetails principalDetails) throws RuntimeException {
        User currentUser = principalDetails.getUser();
        teamService.createTeam(currentUser, teamCreateDTO.getTeamName(), teamCreateDTO.getDescription());
        return "redirect:/team/overview?username=" + currentUser.getUsername();
    }

    // 팀 상세보기 화면 반환
    @GetMapping
    public String teamDetail(@RequestParam Long teamId,
                             Model model) {
        Team findTeam = teamService.getTeam(teamId);
        model.addAttribute("team", findTeam.entityToTeamInfoDTO());
        model.addAttribute("teamId", teamId);

        List<TeamUser> findMembers = teamUserService.getMembers(findTeam);
        List<TeamUserInfoDTO> members = findMembers.stream().map(TeamUser::entityToTeamUserInfoDTO).collect(Collectors.toList());

        List<Bundle> linkedBundles = bundleService.getLinkedTeam(findTeam);
        List<BundleInfoDTO> bundles = linkedBundles.stream().map(Bundle::entityToInfoDTO).collect(Collectors.toList());
        model.addAttribute("members", members);
        model.addAttribute("bundles", bundles);
        return "team/teamDetail";
    }

    // 팀에 번들 추가
    @GetMapping("/bundle")
    public String addBundle(@RequestParam Long bundleId,
                            @RequestParam Long teamId) {
        Team findTeam = teamService.getTeam(teamId);
        Bundle findBundle = bundleService.findById(bundleId).orElseThrow(BundleNotFoundException::new);
        teamService.addBundle(findTeam, findBundle);
        return "redirect:/team?teamId=" + teamId;
    }

    // 팀에 유저 추가
    @GetMapping("/user")
    public String addMember(@RequestParam String username,
                            @RequestParam Long teamId) {
        User findUser = userService.getUser(username);
        Team findTeam = teamService.getTeam(teamId);
        teamUserService.addMember(findTeam, findUser);
        return "redirect:/team?teamId=" + teamId;
    }

}
