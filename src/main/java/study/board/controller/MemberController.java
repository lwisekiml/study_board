package study.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import study.board.entity.Member;
import study.board.service.MemberService;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // http://localhost:8080/members/add
    @GetMapping("/add")
    public String addForm() {
        return "members/addMemberForm";
    }

    // http://localhost:8080/members/add
    @PostMapping("/add")
    public String save(
            @RequestParam("username") String username,
            @RequestParam("loginId") String loginId,
            @RequestParam("password") String password
    ) {
        System.out.println("username = " + username);
        System.out.println("loginId = " + loginId);
        System.out.println("password = " + password);

        // 회원 가입 정보를 확인하는 창을 만들때 member를 사용할 예정
        Member member = memberService.addMember(username, loginId, password);

        return "redirect:/members/welcome";
    }

    // http://localhost:8080/welcome
    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }
}
