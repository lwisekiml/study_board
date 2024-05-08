package study.board.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PostController {

    @GetMapping("/posts/new")
    public String createForm(HttpServletRequest request) {
        return "posts/createPostForm";
    }

    @PostMapping("/posts/new")
    public String create(
            @RequestParam("title") String title,
            @RequestParam("content") String content
    ) {
        System.out.println("title = " + title);
        System.out.println("content = " + content);

        return "redirect:/";
    }
}
