package study.board.board;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import study.board.comment.CommentDto;
import study.board.file.UploadFile;
import study.board.file.UploadFiles;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    private List<CommentDto> comments = new ArrayList<>();

    // 첨부파일
    private UploadFile attachFile;
    private List<UploadFiles> imageFiles = new ArrayList<>();

    private String createdBy;
    private String createdDate;
    private String lastModifiedBy;
    private String lastModifiedDate;

    public static BoardDto toDto(Board entity) {
        return new BoardDto(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getViews(),
                entity.getComments().stream().map(CommentDto::toCommentDto).collect(Collectors.toList()),
                entity.getAttachFile(),
                entity.getImageFiles(),
                entity.getCreatedBy(),
                entity.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                entity.getLastModifiedBy(),
                entity.getLastModifiedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        );
    }
}
