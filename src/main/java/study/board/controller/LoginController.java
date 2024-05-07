package study.board.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.board.dto.LoginFormDto;
import study.board.service.LoginService;
import study.board.session.SessionConst;
import study.board.session.SessionManager;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final SessionManager sessionManager;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginFormDto") LoginFormDto form) {
        return "/login/loginForm";
    }

    @PostMapping("/login")
    public String login(
            @ModelAttribute LoginFormDto form,
            HttpServletRequest request
    ) {

        LoginFormDto loginFormDto = loginService.login(form.getLoginId(), form.getPassword());
        log.info("login : {}", loginFormDto);

        if (loginFormDto == null) {
            // 해당 회원이 없을 경우
            // 아이디 또는 비밀번호가 맞지 않습니다.
            return "login/loginForm";
        }

        // 로그인 처리
        // 쿠기에 시간 정보를 주지 않으면 세션 쿠키(브라우저 종료시 모두 종료)
//        Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
//        response.addCookie(idCookie);

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginFormDto);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    private void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
