package study.board.member;

import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {

    private Long memberId;
    private String loginId;
    private String memberName;
    private String email;
    private MemberRole memberRole;

    public static MemberResponseDto toMemberResponseDto(Member entity) {
        return new MemberResponseDto(
                entity.getMemberId(),
                entity.getLoginId(),
                entity.getMemberName(),
                entity.getEmail(),
                entity.getMemberRole()
        );
    }
}
