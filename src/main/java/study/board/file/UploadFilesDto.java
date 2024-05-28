package study.board.file;

import lombok.*;
import study.board.board.Board;

import java.time.format.DateTimeFormatter;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UploadFilesDto {

    private Long id;
    private Board board;

    private String uploadFileName; // 고객이 업로드한 파일명
    private String storeFileName;  // 서버 내부에서 관리하는 파일명

    private String createdBy;
    private String createdDate;

    public static UploadFilesDto toUploadFilesDto(UploadFiles entity) {
        return new UploadFilesDto(
                entity.getId(),
                entity.getBoard(),
                entity.getUploadFileName(),
                entity.getStoreFileName(),
                entity.getCreatedBy(),
                entity.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        );
    }
}
