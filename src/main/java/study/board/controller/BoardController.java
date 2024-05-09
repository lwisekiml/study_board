package study.board.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.board.argumentresolver.Login;
import study.board.dto.LoginFormDto;
import study.board.entity.Post;
import study.board.repository.PostRepository;
import study.board.session.SessionConst;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final LoginController loginController;
    private final PostRepository postRepository;

    @GetMapping("/")
    public String list(
            @Login LoginFormDto loginFormDto,
            Model model,
            HttpServletRequest request
    ) {

        // 접속시 로그인 상태로 하기 위함
        loginController.login(new LoginFormDto(null, "test", "1234"), "/", request);
        HttpSession session = request.getSession();
        loginFormDto = (LoginFormDto) session.getAttribute(SessionConst.LOGIN_MEMBER);

        // session에 회원 데이터가 없으면 board로 이동
        if (loginFormDto == null) {
            return "board";
        }

        List<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);

        // session이 유지되면 로그인으로 이동
        model.addAttribute("loginFormDto", loginFormDto);
        return "loginBoard";
    }

    // 게시글 클릭
    @GetMapping("/board")
    public String board(@RequestParam("id") long id) {
        System.out.println("id = " + id);

        return "post";
    }
}
