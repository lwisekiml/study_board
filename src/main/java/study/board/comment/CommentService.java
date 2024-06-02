package study.board.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.board.board.Board;
import study.board.board.dto.BoardDto;
import study.board.board.BoardService;
import study.board.member.Member;
import study.board.member.MemberRepository;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final BoardService boardService;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void create(Long boardId, String loginId, String commentContent) {
        Board board = boardService.findBoard(boardId);
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(IllegalArgumentException::new);
        Comment comment = new Comment(board, member, commentContent);
        commentRepository.save(comment);
    }

    @Transactional
    public void deleteById(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Transactional
    public void edit(CommentDto commentDto) {
        Comment comment = this.findComment(commentDto.getCommentId());
        comment.setCommentContent(commentDto.getCommentContent());
    }

    @Transactional
    public CommentDto findCommentToCommentDto(Long commentId) {
        return CommentDto.toCommentDto(this.findComment(commentId));
    }

    @Transactional
    public Comment findComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(IllegalArgumentException::new);
    }

//    @Transactional
//    public BoardDto findBoardDto(Long commentId) {
//        Long boardId = this.findComment(commentId).getBoard().getId();
//        return boardService.findBoardToBoardDto(boardId);
//    }
}
