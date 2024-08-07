package study.board.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member/signup")
    public String signup(
            @ModelAttribute("memberDto") MemberDto memberDto
    ) {
        return "member/signup";
    }


    @PostMapping("/member/signup")
    public String signup(
            @Validated @ModelAttribute("memberDto") MemberDto memberDto,
            BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "member/signup";
        }

        // 비밀번호와 비밀번호 확인 값이 같은지 검증
        if (!memberDto.getPassword().equals(memberDto.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "passwordInCorrect", "비밀번호와 비밀번호 확인의 값이 일치하지 않습니다.");
            return "member/signup";
        }

        try {
            memberService.create(memberDto);
        } catch (DataIntegrityViolationException e) {
            log.error("signupFailed!! Duplicate!! " + memberDto, e);
            // 아이디 중복인지 이메일 중복인지 분리하려 했으나 방법을 못찾았다.
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다."); // 글로벌 오류
            return "member/signup";
        } catch (Exception e) {
            log.error("signupFailed!! " + memberDto, e);
            bindingResult.reject("signupFailed", e.getMessage());
            return "member/signup";
        }

        return "redirect:/";
    }

    @GetMapping("/member/login")
    public String login() {
        return "member/loginForm";
    }
}
