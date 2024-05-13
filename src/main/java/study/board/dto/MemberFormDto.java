package study.board.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class MemberFormDto {

    private String username;
    private String loginId;
    private String password;

    public MemberFormDto() {
    }

}
