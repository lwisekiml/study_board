package study.board.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import study.board.board.dto.BoardDto;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment/{boardId}/create")
    public String create(
            @PathVariable("boardId") Long boardId
            , @RequestParam(value = "commentContent") String commentContent
            , Principal principal
    ) {
        commentService.create(boardId, principal.getName(), commentContent);
        return String.format("redirect:/board/%s", boardId);
    }

    @PostMapping("/comment/{commentId}/delete")
    public String deleteById(
            @PathVariable("commentId") Long commentId,
            @ModelAttribute("boardDto") BoardDto boardDto
    ) {
        commentService.deleteById(commentId);
        return String.format("redirect:/board/%s", boardDto.getId());
    }

    @GetMapping("/comment/{commentId}/edit")
    public String editForm(
            @PathVariable("commentId") Long commentId,
            Model model
    ) {
        CommentDto commentDto = commentService.findCommentToCommentDto(commentId);
        BoardDto boardDto = commentService.findBoardDto(commentId);

        model.addAttribute("boardDto", boardDto);
        model.addAttribute("commentDto", commentDto);

        return "board/editCommentForm";
    }

    @PostMapping("/comment/{commentId}/edit")
    public String edit(
            @ModelAttribute("commentDto") CommentDto commentDto,
            @ModelAttribute("boardDto") BoardDto boardDto
    ) {
        commentService.edit(commentDto);
        return String.format("redirect:/board/%s", boardDto.getId());
    }

}
