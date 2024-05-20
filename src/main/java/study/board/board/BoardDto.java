package study.board.board;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter @Setter
@AllArgsConstructor
public class BoardDto {

    private Long id;

    @NotBlank(message = "제목은 필수 입니다.")
    private String title;
    @NotBlank(message = "내용은 필수 입니다.")
    private String content;
    private int views; // 조회수

    // 첨부파일
    private MultipartFile attachFile; // Board에 없다.
    private String uploadFileName; // 고객이 업로드한 파일명
    private String storeFileName; // 서버 내부에서 관리하는 파일명

    public BoardDto() {
    }

    // 본인 글 볼때 사용
    public BoardDto(Long id, String title, int views) {
        this.id = id;
        this.title = title;
        this.views = views;
    }

    public BoardDto(Long id, String title, String content, int views) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.views = views;
    }

    public BoardDto(Long id, String title, String content, int views, String uploadFileName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.views = views;
        this.uploadFileName = uploadFileName;
    }

    public BoardDto(Long id, String title, String content, int views, String uploadFileName, String storeFileName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.views = views;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

    public static BoardDto toDto(Board entity) {
        return new BoardDto(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getViews(),
                entity.getUploadFileName(),
                entity.getStoreFileName()
        );
    }
}
