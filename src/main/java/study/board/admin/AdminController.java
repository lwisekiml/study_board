package study.board.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import study.board.member.MemberResponseDto;
import study.board.member.MemberService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;

    @GetMapping("/admin")
    public String admin(
            Model model
    ) {
        List<MemberResponseDto> allMember = memberService.findAll();
        model.addAttribute("allMember", allMember);
        return "admin/main";
    }

    @GetMapping("/admin/{id}/delete")
    public String adminDelete(
            @PathVariable("id") String id
    ) {
        memberService.deleteMember(Long.parseLong(id));
        return "redirect:/admin";
    }
}
