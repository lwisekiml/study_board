package study.board.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberFormDto {

    @NotEmpty
    private String username;
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;

    public MemberFormDto() {
    }

}
