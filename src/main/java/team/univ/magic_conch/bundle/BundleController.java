package team.univ.magic_conch.bundle;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import team.univ.magic_conch.auth.PrincipalDetails;
import team.univ.magic_conch.bundle.dto.BundleCreateDTO;
import team.univ.magic_conch.bundle.dto.BundleInfoDTO;
import team.univ.magic_conch.bundle.exception.BundleNotFoundException;
import team.univ.magic_conch.question.QuestionService;
import team.univ.magic_conch.tag.Tag;
import team.univ.magic_conch.tag.TagRepository;
import team.univ.magic_conch.tag.dto.TagDTO;
import team.univ.magic_conch.user.User;
import team.univ.magic_conch.user.UserRepository;
import team.univ.magic_conch.user.exception.UserNotFoundException;
import team.univ.magic_conch.utils.page.PageResultDTO;
import team.univ.magic_conch.bundle.AccessLevel;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class BundleController {

    private final TagRepository tagRepository;
    private final BundleService bundleService;
    private final UserRepository userRepository;
    private final QuestionService questionService;

    // 번들 생성 폼 보여주기
    @GetMapping("/bundle/createForm")
    public String bundleForm(Model model) {
        model.addAttribute("bundleForm", new BundleCreateDTO());

        List<TagDTO> tags = tagRepository.findAll().stream()
                .map(Tag::entityToTagDto)
                .collect(Collectors.toList());
        model.addAttribute("tags", tags);
        return "bundleCreateForm";
    }

    // 번들 생성 처리 (생성 완료되면 마이페이지 오버뷰로 이동)
    @PostMapping("/bundle")
    public String createBundle(@ModelAttribute BundleCreateDTO bundleCreateDTO,
                               @AuthenticationPrincipal PrincipalDetails principalDetails) {
        Tag findTag = tagRepository.findByName(bundleCreateDTO.getTagName());
        Bundle createdBundle = bundleService.createBundle(bundleCreateDTO.getBundleName(),
                findTag,
                principalDetails.getUser(),
                AccessLevel.valueOf(bundleCreateDTO.getAccessLevel()));
        String username = principalDetails.getUsername();
        return "redirect:/bundle/overview" + "?username=" + username;
    }

    // 번들 상세보기 화면
    // TODO 해당 번들에 질문을 올린 사용자 수 체킹 구현
    @GetMapping("/bundle")
    public String showBundleDetails(@RequestParam Long bundleId, Model model,
                                    @PageableDefault(size = 10) Pageable pageable) {

        Bundle findBundle = bundleService.findById(bundleId).orElseThrow(BundleNotFoundException::new);
        model.addAttribute("bundle", findBundle.entityToInfoDTO());
        model.addAttribute("questions", questionService.getQuestionsByBundleId(bundleId, pageable));
        return "bundle/bundleDetail";
    }

    @GetMapping("/bundle/overview")
    public String overview(@RequestParam String username,
                           Pageable pageable, Model model) {
        // 열람 대상이 되는 유저
        User targetUser = userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);

        // 대상 유저의 번들 가져오기
        Page<Bundle> result = bundleService.getBundleByUsername(username, pageable);
        model.addAttribute("username", username);
        model.addAttribute("result",
                new PageResultDTO<>(result, Bundle::entityToInfoDTO));
        return "bundle/overview";
    }

    @GetMapping("/bundle/search")
    @ResponseBody
    public ResponseEntity<List<BundleInfoDTO>> searchBundle(@RequestParam String bundleName) {
        List<Bundle> searchedBundles = bundleService.searchBundle(bundleName);
        if (searchedBundles.size() == 0) {
            return ResponseEntity.noContent().build();
        }
        List<BundleInfoDTO> bundles = searchedBundles.stream().map(Bundle::entityToInfoDTO).collect(Collectors.toList());
        return ResponseEntity.ok().body(bundles);
    }
}
