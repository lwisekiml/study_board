package study.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import study.board.dto.MemberFormDto;
import study.board.service.MemberService;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // http://localhost:8080/members/add
    @GetMapping("/add")
    public String addForm(@ModelAttribute("memberFormDto") MemberFormDto memberFormDto) {
        return "members/addMemberForm";
    }

    // http://localhost:8080/members/add
    @PostMapping("/add")
    public String save(@ModelAttribute("memberFormDto") MemberFormDto memberFormDto) {

        // 아이디 중복 확인 필요
        memberService.save(memberFormDto.getUsername(), memberFormDto.getLoginId(), memberFormDto.getPassword());

        return "redirect:/members/welcome";
    }

    // http://localhost:8080/welcome
    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

}
