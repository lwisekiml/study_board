package study.board.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.board.dto.LoginFormDto;
import study.board.service.LoginService;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/loginForm")
    public String loginForm() {
        return "/login/loginForm";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam("loginId") String loginId,
            @RequestParam("password") String password,
            HttpServletResponse response
    ) {
        LoginFormDto loginMember = loginService.login(loginId, password);

        if (loginMember == null) {
            // 해당 회원이 없을 경우
            // 아이디 또는 비밀번호가 맞지 않습니다.
            return "login/loginForm";
        }

        // 로그인 처리
        // 쿠기에 시간 정보를 주지 않으면 세션 쿠키(브라우저 종료시 모두 종료)
        Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
        response.addCookie(idCookie);

        return "loginBoard";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        expireCookie(response, "memberId");
        return "redirect:/";
    }

    private void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
