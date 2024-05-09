package study.board.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import study.board.dto.LoginFormDto;
import study.board.dto.PostFormDto;

@Controller
public class PostController {

    @GetMapping("/posts/new")
    public String createForm(
            @ModelAttribute("loginFormDto") LoginFormDto loginFormDto
    ) {
        return "posts/createPostForm";
    }

    @PostMapping("/posts/new")
    public String create(
            @ModelAttribute PostFormDto form,
            HttpServletRequest request
    ) {


        return "redirect:/";
    }
}
