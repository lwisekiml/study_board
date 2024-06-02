package study.board.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import study.board.board.Board;
import study.board.file.UploadFileDto;
import study.board.file.UploadFilesDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardGetEditDto {

    Long id;
    private String loginId;
    private String title;
    private String content;

    private UploadFileDto attachFile;
    private List<UploadFilesDto> imageFiles = new ArrayList<>();

    public static BoardGetEditDto toBoardGetEditDto(Board entity) {
        return new BoardGetEditDto(
                entity.getId(),
                entity.getMember().getLoginId(),
                entity.getTitle(),
                entity.getContent(),
                UploadFileDto.toUploadFileDto(entity.getAttachFile()),
                entity.getImageFiles().stream().map(UploadFilesDto::toUploadFilesDto).collect(Collectors.toList())
        );
    }
}
