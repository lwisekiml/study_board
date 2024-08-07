package study.board.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

    @NotBlank(message = "아이디는 필수 입니다.")
    private String loginId;

//    @Pattern(regexp = "^[A-Za-z가-힣]+$", message = "사용자 이름은 한글 또는 알파벳만 입력해주세요.")
    @NotBlank(message = "이름은 필수 입니다.")
    private String memberName;

    @NotBlank(message = "이메일은 필수 입니다.")
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    //    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}", message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    @NotBlank(message = "비밀번호는 필수 입니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인은 필수 입니다.")
    private String passwordConfirm;

//    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "10 ~ 11 자리의 숫자만 입력 가능합니다."
//    private String phoneNumber;
//    private String address;

    public static String toLoginId(Member entity) {
        return entity.getLoginId();
    }

    public MemberDto(String loginId, String memberName, String email) {
        this.loginId = loginId;
        this.memberName = memberName;
        this.email = email;
    }

    public static MemberDto toMemberDto(Member entity) {
        return new MemberDto(
                entity.getLoginId(),
                entity.getMemberName(),
                entity.getEmail()
        );
    }
}
