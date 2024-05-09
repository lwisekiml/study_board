package study.board.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostFormDto {

    @NotEmpty
    private String memberName;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;

    public PostFormDto() {
    }

    public PostFormDto(String memberName, String title, String content) {
        this.memberName = memberName;
        this.title = title;
        this.content = content;
    }
}
