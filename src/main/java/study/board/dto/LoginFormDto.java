package study.board.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginFormDto {

    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;

    public LoginFormDto() {
    }

    public LoginFormDto(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }
}
