package team.univ.magic_conch.bundle;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import team.univ.magic_conch.auth.PrincipalDetails;
import team.univ.magic_conch.bundle.dto.BundleCreateDTO;
import team.univ.magic_conch.bundle.dto.BundleSimpleDTO;
import team.univ.magic_conch.bundle.exception.BundleNotFoundException;
import team.univ.magic_conch.tag.Tag;
import team.univ.magic_conch.tag.TagRepository;
import team.univ.magic_conch.tag.dto.TagDTO;
import team.univ.magic_conch.user.User;
import team.univ.magic_conch.user.UserRepository;
import team.univ.magic_conch.user.exception.UserNotFoundException;
import team.univ.magic_conch.utils.mapper.DTOMapper;
import team.univ.magic_conch.utils.page.PageResultDTO;
import team.univ.magic_conch.visibility.UserRelation;
import team.univ.magic_conch.visibility.UserRelationService;
import team.univ.magic_conch.visibility.Visibility;
import team.univ.magic_conch.visibility.VisibilityChecker;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@SessionAttributes("bundleId")
@RequiredArgsConstructor
@Controller
@RequestMapping("/bundle")
public class BundleController {

    private final TagRepository tagRepository;
    private final BundleService bundleService;
    private final UserRepository userRepository;
    private final VisibilityChecker visibilityChecker;
    private final UserRelationService userRelationService;

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

    @ModelAttribute("bundleId")
    public void setBundleId(Model model) {
        model.addAttribute("bundleId");
    }

    // 번들 상세보기 화면
    @GetMapping("/{id}")
    public String showBundleDetails(@PathVariable Long id, Model model) {
        Bundle findBundle = bundleService.findById(id).orElseThrow(BundleNotFoundException::new);
        model.addAttribute("bundleId", findBundle.getId());
        model.addAttribute("bundle", findBundle.toBundleHeaderDTO());
        return "/bundle/bundleDetails";
    }

    @GetMapping("/overview")
    public String overview(@RequestParam String username, Pageable pageable,
                           @AuthenticationPrincipal PrincipalDetails principalDetails,
                           Model model) {
        Function<Bundle, BundleSimpleDTO> fn = (bundle) -> {
            User targetUser = userRepository.findByUsername(username)
                    .orElseThrow(UserNotFoundException::new);

            User currentUser = null;
            if (principalDetails != null) {
                currentUser = principalDetails.getUser();
            }
            UserRelation userRelation = userRelationService.checkRelation(currentUser, targetUser);
            if (visibilityChecker.isVisible(bundle.getVisibility(), userRelation)) {

                return bundle.entityToSimpleDTO();
            }
            return null;
        };
        model.addAttribute("pageResult",
                new PageResultDTO<>(bundleService.getBundleByUsername(username, pageable), fn));
        return "bundle/overview";
    }
}
