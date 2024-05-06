package study.board.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginFormDto {

    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;

    public LoginFormDto(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }
}
