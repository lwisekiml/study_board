package study.board.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import study.board.board.BoardService;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final BoardService boardService;

    // http://localhost:8080/member/add
    @GetMapping("/add")
    public String addForm(@ModelAttribute("memberFormDto") MemberFormDto memberFormDto) {
        return "member/addMemberForm";
    }

    // http://localhost:8080/member/add
    @PostMapping("/add")
    public String save(@ModelAttribute("memberFormDto") MemberFormDto memberFormDto) {

        // 아이디 중복 확인 필요
        memberService.save(memberFormDto.getUsername(), memberFormDto.getLoginId(), memberFormDto.getPassword());

        return "redirect:/member/welcome";
    }

    // http://localhost:8080/member/welcome
    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
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
