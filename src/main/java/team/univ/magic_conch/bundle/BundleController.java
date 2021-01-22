package team.univ.magic_conch.bundle;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import team.univ.magic_conch.bundle.dto.BundleDTO;
import team.univ.magic_conch.config.auth.PrincipalDetails;
import team.univ.magic_conch.tag.Tag;
import team.univ.magic_conch.tag.TagRepository;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class BundleController {

    private final TagRepository tagRepository;
    private final BundleService bundleService;
    @GetMapping("/bundleForm")
    public String bundleForm(Model model) {
        model.addAttribute("bundleForm", new BundleDTO.BundleForm());
        model.addAttribute("tags",tagRepository.findAll());
        return "form/addBundleForm";
    }

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

}
