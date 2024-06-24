package study.board.oauth2;

import jakarta.persistence.*;
import lombok.*;
import study.board.member.Member;
import study.board.member.MemberRole;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserEntity {
    @Id @GeneratedValue
    private Long id;
    private String username;
    private String email;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
