package study.board.file;

import lombok.*;
import study.board.board.Board;

import java.time.format.DateTimeFormatter;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UploadFileDto {

    private Long id;
    private Board board;

    private String uploadFileName; // 고객이 업로드한 파일명
    private String storeFileName;  // 서버 내부에서 관리하는 파일명

    private String createdBy;
    private String createdDate;

    public static UploadFileDto toUploadFileDto(UploadFile entity) {
        if (entity == null)
            return null;

        return new UploadFileDto(
                entity.getId(),
                entity.getBoard(),
                entity.getUploadFileName(),
                entity.getStoreFileName(),
                entity.getCreatedBy(),
                entity.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        );
    }
}
