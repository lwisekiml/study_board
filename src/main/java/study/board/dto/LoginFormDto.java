package study.board.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginFormDto {

    private Long id;

    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;

    public LoginFormDto(Long id, String loginId, String password) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
    }
}
