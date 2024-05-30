package study.board.board;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import study.board.base.BaseEntity;
import study.board.comment.Comment;
import study.board.file.UploadFile;
import study.board.file.UploadFiles;
import study.board.member.Member;

import java.util.ArrayList;
import java.util.List;

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

//    @ManyToOne
//    @JoinColumn(name = "member_id")
//    private Member member;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    // 첨부파일
    @OneToOne(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UploadFile attachFile;

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
    public Board(String title, String content, UploadFile uploadFile, List<UploadFiles> imageFiles) {
        this.title = title;
        this.content = content;
        this.setAttachFile(uploadFile);
        this.setImageFiles(imageFiles);
    }

    public void setAttachFile(UploadFile attachFile) {
        this.attachFile = attachFile;
        if (attachFile != null) {
            attachFile.setBoard(this); // board_id 생성됨
        }
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
