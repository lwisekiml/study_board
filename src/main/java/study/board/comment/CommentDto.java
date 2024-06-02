package study.board.comment;

import lombok.*;
import study.board.board.dto.BoardDto;
import study.board.member.MemberDto;

import java.time.format.DateTimeFormatter;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long commentId;
    private Long boardId;
    private String loginId;
    private String commentContent;

    private String createdBy;
    private String createdDate;
    private String lastModifiedBy;
    private String lastModifiedDate;

    public static CommentDto toCommentDto(Comment entity) {
        return new CommentDto(
                entity.getCommentId(),
                BoardDto.toBoardId(entity.getBoard()),
                MemberDto.toLoginId(entity.getMember()), // 댓글에 로그인 아이디 표시를 위함
                entity.getCommentContent(),
                entity.getCreatedBy(),
                entity.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                entity.getLastModifiedBy(),
                entity.getLastModifiedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        );
    }
}
