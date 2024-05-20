package study.board.board;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String title;
    private String content;
    private int views; // 조회수

    // 첨부파일
    private String uploadFileName; // 고객이 업로드한 파일명
    private String storeFileName; // 서버 내부에서 관리하는 파일명

    // TestDataInit
    public Board(String title, String content, int views) {
        this.title = title;
        this.content = content;
        this.views = views;
    }

    // 글쓰기
    // @NoArgsConstructor와 @Builder를 같이 사용하면 오류 발생하여 생성자에 붙인다.
    @Builder
    public Board(String title, String content, String uploadFileName, String storeFileName) {
        this.title = title;
        this.content = content;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

    public void plusViews() {
        this.views += 1;
    }

}
