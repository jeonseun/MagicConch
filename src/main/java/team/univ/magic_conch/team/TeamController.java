package team.univ.magic_conch.team;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import team.univ.magic_conch.auth.PrincipalDetails;
import team.univ.magic_conch.team.dto.TeamCreateDTO;
import team.univ.magic_conch.team.dto.TeamInfoDTO;
import team.univ.magic_conch.user.User;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping("/team")
public class TeamController {

    private final TeamService teamService;

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
        model.addAttribute("team", teamService.getTeam(teamId));
        return "team/teamDetail";
    }

}
