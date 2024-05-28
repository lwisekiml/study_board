package study.board.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.board.board.BoardDto;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment/{boardId}/create")
    public String create(@PathVariable("boardId") Long boardId, @RequestParam(value = "commentContent") String commentContent) {
        commentService.create(boardId, commentContent);
        return String.format("redirect:/board/%s", boardId);
    }

    @PostMapping("/comment/{commentId}/delete")
    public String deleteById(@PathVariable("commentId") Long commentId, @ModelAttribute("boardDto") BoardDto boardDto) {
        commentService.deleteById(commentId);
        return String.format("redirect:/board/%s", boardDto.getId());
    }
}
