package study.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.board.entity.Member;
import study.board.repository.MemberRepository;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final MemberRepository memberRepository;

    @GetMapping("/")
    public String list(
            @CookieValue(name = "memberId", required = false) Long memberId,
            Model model
    ) {

        if (memberId == null) {
            return "board";
        }

        // 로그인
        Optional<Member> findMember = memberRepository.findById(memberId);
        if (findMember.isEmpty()) {
            return "board";
        }

        model.addAttribute("member", findMember.get());
        return "loginBoard";
    }

    // 게시글 클릭
    @GetMapping("/board")
    public String board(@RequestParam("id") int id) {
        System.out.println("id = " + id);

        return "post";
    }
}
