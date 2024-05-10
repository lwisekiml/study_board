package study.board.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import study.board.dto.LoginFormDto;
import study.board.dto.PostFormDto;
import study.board.entity.Post;
import study.board.repository.PostRepository;
import study.board.service.PostService;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;

    private final PostService postService;

    @GetMapping("/posts/new")
    public String createForm(
            HttpServletRequest request,
            Model model
    ) {
        LoginFormDto loginFormDto = (LoginFormDto) request.getSession().getAttribute("loginMember");
        model.addAttribute("loginFormDto", loginFormDto);
        return "posts/createPostForm";
    }

    @PostMapping("/posts/new")
    public String create(
            @ModelAttribute("loginFormDto") LoginFormDto loginFormDto,
            @ModelAttribute("PostFormDto") PostFormDto form,
            HttpServletRequest request
    ) {
        form.setLoginId(loginFormDto.getLoginId());
        postRepository.save(new Post(form.getLoginId(), form.getTitle(), form.getContent()));

        return "redirect:/";
    }

    @GetMapping("/board/posts/{postId}")
    public String post(@PathVariable(name = "postId") Long postId, Model model) {
        Post post = postRepository.findById(postId).get();
        model.addAttribute("PostFormDto", new PostFormDto(post.getId(), post.getLoginId(), post.getTitle(), post.getContent()));
        return "posts/post";
    }

    @PostMapping("/posts/delete")
    public String delete(
            @ModelAttribute PostFormDto form
    ) {
        Post post = postRepository.findById(form.getId()).get();
        postRepository.delete(post);
        return "redirect:/";
    }

    @GetMapping("/{postId}/edit")
    public String editForm(@PathVariable(name = "postId") Long postId, Model model) {
        Post post = postRepository.findById(postId).get();
        model.addAttribute("postFormDto", new PostFormDto(post.getId(), post.getLoginId(), post.getTitle(), post.getContent()));
        return "posts/editForm";
    }

    // 상품 수정 폼에서 저장 클릭
    @PostMapping("/{postId}/edit")
    public String edit(@ModelAttribute PostFormDto postFormDto) {

        postService.edit(postFormDto);

//        Post post = postRepository.findById(postFormDto.getId()).get();
//
//        // entity 변경 감지로 수정 됨
//        post.setTitle(postFormDto.getTitle());
//        post.setContent(postFormDto.getContent());

        return "redirect:/board/posts/{postId}";
    }
}
