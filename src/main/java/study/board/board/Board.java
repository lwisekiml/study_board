package study.board.board;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import study.board.base.BaseEntity;
import study.board.file.UploadFiles;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String title;
    private String content;
    private int views; // 조회수

    // 첨부파일
    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "uploadfile_id")
    private UploadFiles attachFile;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<UploadFiles> imageFiles = new ArrayList<>();

    // TestDataInit
    public Board(String title, String content, int views) {
        this.title = title;
        this.content = content;
        this.views = views;
    }

    // 글쓰기
    // @NoArgsConstructor와 @Builder를 같이 사용하면 오류 발생하여 생성자에 붙인다.
//    @Builder
    public Board(String title, String content, UploadFiles uploadFiles, List<UploadFiles> imageFiles) {
        this.title = title;
        this.content = content;
        this.setAttachFile(uploadFiles);
        this.setImageFiles(imageFiles);
    }

    public void setImageFiles(List<UploadFiles> imageFiles) {
        for (UploadFiles imageFile : imageFiles) {
            this.imageFiles.add(imageFile);
            imageFile.setBoard(this); // board_id 생성됨
        }
    }

    public void plusViews() {
        this.views += 1;
    }

}
