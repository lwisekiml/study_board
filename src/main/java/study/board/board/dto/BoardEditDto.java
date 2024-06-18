package study.board.board.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import study.board.board.Board;
import study.board.file.UploadFileDto;
import study.board.file.UploadFilesDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardEditDto {

    Long id;

    private String loginId;
    @NotBlank(message = "제목은 필수 입니다.")
    private String title;
    @NotBlank(message = "내용은 필수 입니다.")
    private String content;

    // post 요청시 사용
    private MultipartFile mulAttachFile;
    private List<MultipartFile> mulImageFiles;

    // get 요청시 사용
    private UploadFileDto attachFile;
    private List<UploadFilesDto> imageFiles = new ArrayList<>();

    public BoardEditDto(Long id, String loginId, String title, String content, UploadFileDto attachFile, List<UploadFilesDto> imageFiles) {
        this.id = id;
        this.loginId = loginId;
        this.title = title;
        this.content = content;
        this.attachFile = attachFile;
        this.imageFiles = imageFiles;
    }

    // get 글 수정
    public static BoardEditDto toBoardEditDto(Board entity) {
        return new BoardEditDto(
                entity.getId(),
                entity.getLoginId(),
                entity.getTitle(),
                entity.getContent(),
                UploadFileDto.toUploadFileDto(entity.getAttachFile()),
                entity.getImageFiles().stream().map(UploadFilesDto::toUploadFilesDto).collect(Collectors.toList())
        );
    }
}
