package study.board.board;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import study.board.comment.CommentDto;
import study.board.file.UploadFileDto;
import study.board.file.UploadFilesDto;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto { // 게시물 리스트, 글 수정 때 사용

    private Long id;
    // Member에는 다른 정보들이 있어 로그인 아디디만 따로 뽑아서 넣음
    private String loginId;

    @NotBlank(message = "제목은 필수 입니다.")
    private String title;
    @NotBlank(message = "내용은 필수 입니다.")
    private String content;
    private int views; // 조회수

    private List<CommentDto> comments = new ArrayList<>();

    // 첨부파일
    private UploadFileDto attachFile;
    private List<UploadFilesDto> imageFiles = new ArrayList<>();

    private String createdBy;
    private String createdDate;
    private String lastModifiedBy;
    private String lastModifiedDate;

    public static BoardDto toBoardDto(Board entity) {
        return new BoardDto(
                entity.getId(),
                entity.getMember().getLoginId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getViews(),
                entity.getComments().stream().map(CommentDto::toCommentDto).collect(Collectors.toList()),
                UploadFileDto.toUploadFileDto(entity.getAttachFile()),
                entity.getImageFiles().stream().map(UploadFilesDto::toUploadFilesDto).collect(Collectors.toList()),
                entity.getCreatedBy(),
                entity.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                entity.getLastModifiedBy(),
                entity.getLastModifiedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        );
    }
}
