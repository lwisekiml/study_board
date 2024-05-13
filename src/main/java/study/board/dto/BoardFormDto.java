package study.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class BoardFormDto {

    private Long id;
    private String loginId;
    private String title;
    private String content;

    public BoardFormDto() {
    }

}
