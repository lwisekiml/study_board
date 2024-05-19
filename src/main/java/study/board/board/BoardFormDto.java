package study.board.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
@AllArgsConstructor
public class BoardFormDto {

    private Long id;

    private String title;
    private String content;
    private int views; // 조회수

    // 첨부파일
    private MultipartFile attachFile; // 멀티파트는 @ModelAttribute에서 사용할 수 있다.
    private String uploadFileName; // 고객이 업로드한 파일명
    private String storeFileName; // 서버 내부에서 관리하는 파일명

    public BoardFormDto() {
    }

    // 본인 글 볼때 사용
    public BoardFormDto(Long id, String title, int views) {
        this.id = id;
        this.title = title;
        this.views = views;
    }

    public BoardFormDto(Long id, String title, String content, int views) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.views = views;
    }

    public BoardFormDto(Long id, String title, String content, int views, String uploadFileName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.views = views;
        this.uploadFileName = uploadFileName;
    }
}
