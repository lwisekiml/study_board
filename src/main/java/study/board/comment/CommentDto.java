package study.board.comment;

import lombok.*;
import study.board.board.Board;
import study.board.member.Member;

import java.time.format.DateTimeFormatter;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long commentId;
    private Board board;
    private Member member;
    private String commentContent;

    private String createdBy;
    private String createdDate;
    private String lastModifiedBy;
    private String lastModifiedDate;

    public static CommentDto toCommentDto(Comment entity) {
        return new CommentDto(
                entity.getCommentId(),
                entity.getBoard(),
                entity.getMember(),
                entity.getCommentContent(),
                entity.getCreatedBy(),
                entity.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                entity.getLastModifiedBy(),
                entity.getLastModifiedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        );
    }
}
