package study.board.oauth2;

import jakarta.persistence.*;
import lombok.*;
import study.board.member.Member;

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
    private String role;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
