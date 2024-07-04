package study.board.comment.dto;

import lombok.*;
import study.board.comment.Comment;
import study.board.member.MemberDto;

import java.time.format.DateTimeFormatter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardCommentDto { // 게시글 조회

    private Long commentId;
    private String loginId;
    private String commentContent;

    private String createdBy;
    private String lastModifiedDate;

    public static BoardCommentDto toBoardCommentDto(Comment entity) {
        return new BoardCommentDto(
                entity.getCommentId(),
                entity.getLoginId(), // 댓글에 로그인 아이디 표시
                entity.getCommentContent(),
                entity.getCreatedBy(),
                entity.getLastModifiedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        );
    }
}