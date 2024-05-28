package study.board.comment;

import lombok.*;
import study.board.board.Board;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long commentId;
    private Board board;
    private String commentContent;

    public static CommentDto toCommentDto(Comment entity) {
        return new CommentDto(
                entity.getCommentId(),
                entity.getBoard(),
                entity.getCommentContent()
        );
    }
}
