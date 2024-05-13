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

    // 첨부파일
    private String uploadFileName; // 고객이 업로드한 파일명
    private String storeFileName; // 서버 내부에서 관리하는 파일명

    public Board(String loginId, String title, String content) {
        this.loginId = loginId;
        this.title = title;
        this.content = content;
    }

    public Board(String loginId, String title, String content, String uploadFileName, String storeFileName) {
        this.loginId = loginId;
        this.title = title;
        this.content = content;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

    public void plusViews() {
        this.views += 1;
    }

}
