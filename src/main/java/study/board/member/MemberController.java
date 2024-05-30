package study.board.member;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import study.board.board.BoardService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member/signup")
    public String signup(
            @ModelAttribute("memberDto") MemberDto memberDto
    ) {
        return "/member/signup";
    }


    @PostMapping("/member/signup")
    public String signup(
            @Validated @ModelAttribute("memberDto") MemberDto memberDto,
            BindingResult bindingResult, Model model
    ) {

        if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "/member/signup";
        }

        // 비밀번호와 비밀번호 확인 값이 같은지 검증
        if (!memberDto.getPassword().equals(memberDto.getPasswordConfirm())) {
            // bindingResult.rejectValue(필드명, 오류 코드, 오류 메시지)
            bindingResult.rejectValue("passwordConfirm", "passwordInCorrect", "비밀번호와 비밀번호 확인의 값이 일치하지 않습니다.");
            return "/member/signup";
        }

        memberService.create(memberDto);
        return "redirect:/member/welcome";
    }



    // 멤버에 대한 게시글이기는 한데 Board에 있어야 하나?
//    @GetMapping("/boardList")
//    public String getMemberWrite(Model model, HttpServletRequest request) {
//        LoginFormDto loginFormDto = (LoginFormDto) request.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
//        List<BoardDto> boardDto = boardService.getMemberWrite(loginFormDto.getLoginId());
//        model.addAttribute("boardDtos", boardDto);
//
//        return "/member/boardList";
//    }
}
