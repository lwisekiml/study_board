package study.board.board.dto;

import lombok.*;
import study.board.board.Board;

import java.time.format.DateTimeFormatter;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListBoardDto implements Comparable<ListBoardDto> {

    private Long id;
    private String loginId;
    private String title;
    private int views;
    private int commentSize;
    private int recommendSize;

    private String createdBy;
    private String createdDate;
    private String lastModifiedBy;
    private String lastModifiedDate;

    public static ListBoardDto toListBoardDto(Board entity) {
        return new ListBoardDto(
                entity.getId(),
                entity.getMember().getLoginId(),
                entity.getTitle(),
                entity.getViews(),
                entity.getComments().size(),
                entity.getRecommend().size(),
                entity.getCreatedBy(),
                entity.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                entity.getLastModifiedBy(),
                entity.getLastModifiedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        );
    }


    // id 내림차순 정렬
    @Override
    public int compareTo(ListBoardDto o) {
        if (this.id > o.id) {
            return -1;
        } else if (this.id < o.id) {
            return 1;
        } else {
            return 0;
        }
    }
}
