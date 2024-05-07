package study.board.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.board.dto.LoginFormDto;
import study.board.entity.Member;
import study.board.repository.MemberRepository;
import study.board.session.SessionManager;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final MemberRepository memberRepository;

    private final SessionManager sessionManager;

    @GetMapping("/")
    public String list(HttpServletRequest request,Model model) {

        LoginFormDto loginFormDto = (LoginFormDto) sessionManager.getSession(request);
        if (loginFormDto == null) {
            return "board";
        }

        model.addAttribute("loginFormDto", loginFormDto);
        return "loginBoard";
    }

    // 게시글 클릭
    @GetMapping("/board")
    public String board(@RequestParam("id") int id) {
        System.out.println("id = " + id);

        return "post";
    }
}
