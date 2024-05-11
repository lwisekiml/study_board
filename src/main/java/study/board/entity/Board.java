package study.board.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String loginId;
    private String title;
    private String content;
    private int views; // 조회수

    public Board(String loginId, String title, String content) {
        this.loginId = loginId;
        this.title = title;
        this.content = content;
    }
}
