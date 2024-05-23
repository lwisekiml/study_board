package study.board.board;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import study.board.file.UploadFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {

    private Long id;

    @NotBlank(message = "제목은 필수 입니다.")
    private String title;
    @NotBlank(message = "내용은 필수 입니다.")
    private String content;
    private int views; // 조회수

    // 첨부파일
    private UploadFile attachFile;
    private List<UploadFile> imageFiles = new ArrayList<>();

    private String createdBy;
    private String createdDate;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;


//    // 본인 글 볼때 사용
//    public BoardDto(Long id, String title, int views) {
//        this.id = id;
//        this.title = title;
//        this.views = views;
//    }
//
//    public BoardDto(Long id, String title, String content, int views) {
//        this.id = id;
//        this.title = title;
//        this.content = content;
//        this.views = views;
//    }
//
//    public BoardDto(Long id, String title, String content, int views, String uploadFileName) {
//        this.id = id;
//        this.title = title;
//        this.content = content;
//        this.views = views;
//        this.uploadFileName = uploadFileName;
//    }
//
//    public BoardDto(Long id, String title, String content, int views, String uploadFileName, String storeFileName) {
//        this.id = id;
//        this.title = title;
//        this.content = content;
//        this.views = views;
//        this.uploadFileName = uploadFileName;
//        this.storeFileName = storeFileName;
//    }

    public static BoardDto toDto(Board entity) {
        return new BoardDto(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getViews(),
                entity.getAttachFile(),
                entity.getImageFiles(),
                entity.getCreatedBy(),
                entity.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                entity.getLastModifiedBy(),
                entity.getLastModifiedDate()
        );
    }
}
