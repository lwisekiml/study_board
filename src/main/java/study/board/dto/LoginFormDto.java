package study.board.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginFormDto {

    @NotEmpty
    private String email;
    @NotEmpty
    private String password;

    public LoginFormDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
