package study.board.board;

import jakarta.persistence.*;
import lombok.*;
import study.board.file.UploadFile;

import static jakarta.persistence.FetchType.LAZY;

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
    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "uploadfile_id")
    private UploadFile attachFile;

    // TestDataInit
    public Board(String title, String content, int views) {
        this.title = title;
        this.content = content;
        this.views = views;
    }

    // 글쓰기
    // @NoArgsConstructor와 @Builder를 같이 사용하면 오류 발생하여 생성자에 붙인다.
    @Builder
    public Board(String title, String content, UploadFile uploadFile) {
        this.title = title;
        this.content = content;
        this.attachFile = uploadFile;
    }

    public void plusViews() {
        this.views += 1;
    }

}
