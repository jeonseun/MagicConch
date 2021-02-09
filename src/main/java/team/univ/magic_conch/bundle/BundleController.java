package team.univ.magic_conch.bundle;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import team.univ.magic_conch.auth.PrincipalDetails;
import team.univ.magic_conch.bundle.dto.BundleCreateDTO;
import team.univ.magic_conch.bundle.dto.BundleDTO;
import team.univ.magic_conch.tag.Tag;
import team.univ.magic_conch.tag.TagRepository;
import team.univ.magic_conch.tag.dto.TagDTO;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class BundleController {

    private final TagRepository tagRepository;
    private final BundleService bundleService;

    // 번들 생성 폼 보여주기
    @GetMapping("/bundle/createForm")
    public String bundleForm(Model model) {
        model.addAttribute("bundleForm", new BundleCreateDTO());

        List<TagDTO> tags = tagRepository.findAll().stream()
                .map(Tag::entityToTagDto)
                .collect(Collectors.toList());
        model.addAttribute("tags", tags);
        return "bundle/createBundleForm";
    }

    // 번들 생성 처리 (생성 완료되면 마이페이지 오버뷰로 이동)
    @PostMapping("/bundle")
    public String createBundle(@ModelAttribute BundleCreateDTO bundleCreateDTO,
                               @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Tag findTag = tagRepository.findByName(bundleCreateDTO.getTagName());
        Bundle newBundle = Bundle.builder()
                .name(bundleCreateDTO.getBundleName())
                .tag(findTag)
                .user(principalDetails.getUser())
                .visibility(bundleCreateDTO.getVisibility())
                .build();
        bundleService.save(newBundle);

        String username = principalDetails.getUsername();
        return "redirect:/user/bundle" + "?username=" + username;

    }

    // 번들 상세보기 화면
    @GetMapping("/bundle/{id}")
    public String showBundleDetails(@PathVariable Long id, Model model) {
        BundleDTO.BundleDetails bundleDetails = bundleService.getBundleDetails(id);
        model.addAttribute("bundle", bundleDetails);
        return "/bundle/bundleDetails";
    }
}
