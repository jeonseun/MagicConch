package team.univ.magic_conch.bundle;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import team.univ.magic_conch.auth.PrincipalDetails;
import team.univ.magic_conch.bundle.dto.BundleCreateDTO;
import team.univ.magic_conch.bundle.exception.BundleNotFoundException;
import team.univ.magic_conch.tag.Tag;
import team.univ.magic_conch.tag.TagRepository;
import team.univ.magic_conch.tag.dto.TagDTO;
import team.univ.magic_conch.user.User;
import team.univ.magic_conch.user.UserRepository;
import team.univ.magic_conch.user.exception.UserNotFoundException;
import team.univ.magic_conch.utils.page.PageResultDTO;
import team.univ.magic_conch.visibility.UserRelationService;
import team.univ.magic_conch.visibility.Visibility;
import team.univ.magic_conch.visibility.VisibilityChecker;

import java.util.List;
import java.util.stream.Collectors;

@SessionAttributes("bundleId")
@RequiredArgsConstructor
@Controller
@RequestMapping("/bundle")
public class BundleController {

    private final TagRepository tagRepository;
    private final BundleService bundleService;
    private final UserRepository userRepository;

    // 번들 생성 폼 보여주기
    @GetMapping("/createForm")
    public String bundleForm(Model model) {
        model.addAttribute("bundleForm", new BundleCreateDTO());

        List<TagDTO> tags = tagRepository.findAll().stream()
                .map(Tag::entityToTagDto)
                .collect(Collectors.toList());
        model.addAttribute("tags", tags);
        return "bundle/createBundleForm";
    }

    // 번들 생성 처리 (생성 완료되면 마이페이지 오버뷰로 이동)
    @PostMapping("/")
    public String createBundle(@ModelAttribute BundleCreateDTO bundleCreateDTO,
                               @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Tag findTag = tagRepository.findByName(bundleCreateDTO.getTagName());
        bundleService.create(bundleCreateDTO.getBundleName(), findTag, principalDetails.getUser(), Visibility.valueOf(bundleCreateDTO.getVisibility()));
        String username = principalDetails.getUsername();
        return "redirect:/bundle/overview" + "?username=" + username;

    }
//
//    @ModelAttribute("bundleId")
//    public void setBundleId(Model model) {
//        model.addAttribute("bundleId");
//    }

    // 번들 상세보기 화면
    @GetMapping("/{id}")
    public String showBundleDetails(@PathVariable Long id, Model model) {
        Bundle findBundle = bundleService.findById(id).orElseThrow(BundleNotFoundException::new);
        model.addAttribute("bundleId", findBundle.getId());
        model.addAttribute("bundle", findBundle.toBundleHeaderDTO());
        return "bundle/bundleDetail";
    }

    @GetMapping("/overview")
    public String overview(@RequestParam String username, Pageable pageable,
                                                          @AuthenticationPrincipal PrincipalDetails principalDetails,
                                                          Model model) {
        // 열람 대상이 되는 유저
        User targetUser = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        // 대상 유저의 번들 가져오기
        Page<Bundle> result = bundleService.getBundleByUsername(username, pageable);
        model.addAttribute("username", username);
        model.addAttribute("result",
                new PageResultDTO<>(result, Bundle::entityToSimpleDTO));
        return "bundle/overview";
    }
}
