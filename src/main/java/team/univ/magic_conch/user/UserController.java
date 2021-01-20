package team.univ.magic_conch.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import team.univ.magic_conch.config.auth.PrincipalDetails;
import team.univ.magic_conch.user.dto.UserDTO;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    // 회원가입시 비밀번호, 비밀번호 확인 동등 검증용 validator 등록
    @InitBinder("joinForm")
    public void initPasswordMatchValidator(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new PasswordMatchValidator());
    }

    // 초기화면 제공
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // 로그인 폼 제공
    @RequestMapping(value = "/loginForm",
            method = {RequestMethod.GET, RequestMethod.POST})
    public String loginForm() {
        return "form/loginForm";
    }

    // 회원가입 폼 제공
    @GetMapping("/joinForm")
    public String joinForm(Model model) {
        model.addAttribute("joinForm", new UserDTO.JoinForm());
        return "form/joinForm";
    }

    // 회원가입 데이터 검증
    @PostMapping("/join")
    public String join(@Valid @ModelAttribute UserDTO.JoinForm joinForm,
                       BindingResult bindingResult,
                       RedirectAttributes model) {
        if (bindingResult.hasErrors()) {
            return "form/joinForm";
        }

        model.addFlashAttribute("joinData", joinForm.toJoinData());

        return "redirect:/joinProc";
    }

    // 회원가입 처리
    @GetMapping("/joinProc")
    public String joinProc(UserDTO.JoinData joinData) {
        userService.join(joinData.getUsername(), joinData.getPassword(), joinData.getName());
        return "redirect:/";
    }

    // ID 중복확인
    @PostMapping("/idCheck")
    @ResponseBody
    public ResponseEntity idCheck(@Validated @RequestBody UserDTO.IdCheck idCheck) {
        if (userService.isUsernameDuplicate(idCheck.getId())) {
            return ResponseEntity.badRequest().body("사용이 불가능한 ID 입니다.");
        } else {
            return ResponseEntity.ok().body("사용이 가능한 ID 입니다.");
        }
    }

    // 마이페이지 모아보기 페이지 제공
    @GetMapping("/mypage/overview")
    public String overview() {
        return "/mypage/overview";
    }

    // 마이페이지 내 번들보기 페이지 제공
    @GetMapping("/mypage/bundle")
    public String bundle() {
        return "/mypage/bundle";
    }

    // 마이페이지 내 질문보기 페이지 제공
    @GetMapping("/mypage/question")
    public String question() {
        return "/mypage/question";
    }

    // 마이페이지 내 친구들 보기 제공
    @GetMapping("/mypage/friend")
    public String friend() {
        return "/mypage/friend";
    }

    // 마이페이지 내 설정 보기 페이지 제공
    @GetMapping("/mypage/setting")
    public String setting() {
        return "/mypage/setting";
    }

    @PutMapping("/profile/image")
    @ResponseBody
    public ResponseEntity changeProfileImage(MultipartFile profileImage,
                                             @AuthenticationPrincipal PrincipalDetails principalDetails) {
        User currentUser = principalDetails.getUser();
        String profileImagePath = userService.changeProfileImage(profileImage, currentUser.getUsername());
        if (profileImagePath == null) {
            return ResponseEntity.badRequest().body("프로필 사진 변경중 오류가 발행했습니다.");
        }
        // 시큐리티 컨텍스트 내부의 사용자 정보에도 반영되록함
        currentUser.changeProfileImage(profileImagePath);
        return ResponseEntity.ok().body(profileImagePath);
    }
}
