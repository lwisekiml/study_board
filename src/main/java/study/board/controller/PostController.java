package study.board.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import study.board.dto.LoginFormDto;
import study.board.dto.PostFormDto;
import study.board.entity.Post;
import study.board.repository.PostRepository;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;

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

        postRepository.save(new Post("1", form.getTitle(), form.getContent()));

        return "redirect:/";
    }
}
