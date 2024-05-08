package study.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.board.argumentresolver.Login;
import study.board.dto.LoginFormDto;

@Controller
@RequiredArgsConstructor
public class BoardController {

    @GetMapping("/")
    public String list(
            @Login LoginFormDto loginFormDto,
            Model model
    ) {
        
        // session에 회원 데이터가 없으면 board로 이동
        if (loginFormDto == null) {
            return "board";
        }

        // session이 유지되면 로그인으로 이동
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
