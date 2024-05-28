package study.board.comment;

import jakarta.persistence.*;
import lombok.*;
import study.board.base.BaseEntity;
import study.board.board.Board;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Comment extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private String commentContent;

    public Comment(Board board, String commentContent) {
        this.board = board;
        this.commentContent = commentContent;
    }
}
