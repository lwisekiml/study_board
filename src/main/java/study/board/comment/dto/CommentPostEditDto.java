package study.board.comment.dto;

import lombok.*;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentPostEditDto {

    private Long commentId;
    private String commentContent;
}
