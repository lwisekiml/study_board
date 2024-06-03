package study.board.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import study.board.board.dto.BoardDto;
import study.board.comment.dto.CommentPostEditDto;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/comment/{boardId}/create")
    public String create(
            @PathVariable("boardId") Long boardId
            , @RequestParam(value = "commentContent") String commentContent
            , Principal principal
    ) {
        commentService.create(boardId, principal.getName(), commentContent);
        return String.format("redirect:/board/%s", boardId);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/comment/{commentId}/delete")
    public String delete(
            @PathVariable("commentId") Long commentId,
            @ModelAttribute("boardDto") BoardDto boardDto
    ) {
        commentService.deleteById(commentId);
        return String.format("redirect:/board/%s", boardDto.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/comment/{commentId}/edit")
    public String editForm(
            @PathVariable("commentId") Long commentId,
            Model model
    ) {
        model.addAttribute("boardDto", commentService.findBoardDto(commentId));
        model.addAttribute("commentId", commentId);

        return "board/editCommentForm";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/comment/{commentId}/edit")
    public String edit(
            @ModelAttribute("commentPostEditDto") CommentPostEditDto commentPostEditDto,
            @RequestParam("id") Long boardId
//            @ModelAttribute("boardDto") BoardDto boardDto
    ) {
        commentService.edit(commentPostEditDto);
        return String.format("redirect:/board/%s", boardId);
    }

}
