package team.univ.magic_conch.bundle;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import team.univ.magic_conch.bundle.dto.BundleDTO;
import team.univ.magic_conch.config.auth.PrincipalDetails;
import team.univ.magic_conch.tag.Tag;
import team.univ.magic_conch.tag.TagRepository;

import java.time.LocalDate;

@RequiredArgsConstructor
@Controller
public class BundleController {

    private final TagRepository tagRepository;
    private final BundleService bundleService;

    // 번들 생성 폼 보여주기
    @GetMapping("/bundleForm")
    public String bundleForm(Model model) {
        model.addAttribute("bundleForm", new BundleDTO.BundleForm());
        model.addAttribute("tags", tagRepository.findAll());
        return "form/addBundleForm";
    }

    // 번들 생성 처리 (생성 완료되면 마이페이지 오버뷰로 이동)
    @PostMapping("/bundle")
    public String createBundle(@ModelAttribute BundleDTO.BundleForm bundleForm,
                               @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Tag tag = tagRepository.findByName(bundleForm.getTagName());
        Bundle newBundle = Bundle.builder()
                .name(bundleForm.getBundleName())
                .tag(tag)
                .user(principalDetails.getUser())
                .visibility(bundleForm.getVisibility())
                .createDate(LocalDate.now())
                .build();
        bundleService.save(newBundle);
        return "redirect:/mypage/overview";
    }

    // 번들 상세보기 화면
    @GetMapping("/bundle/{id}")
    public String showBundleDetails(@PathVariable Long id, Model model) {
        BundleDTO.BundleDetails bundleDetails = bundleService.getBundleDetails(id);
        model.addAttribute("bundle", bundleDetails);
        return "/bundle/bundleDetails";
    }
}
