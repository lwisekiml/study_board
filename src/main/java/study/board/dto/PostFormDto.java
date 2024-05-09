package study.board.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostFormDto {

    @NotEmpty
    private String loginId;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;

    public PostFormDto() {
    }

    public PostFormDto(String loginId, String title, String content) {
        this.loginId = loginId;
        this.title = title;
        this.content = content;
    }
}
