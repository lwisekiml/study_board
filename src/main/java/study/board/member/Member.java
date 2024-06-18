package study.board.member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long memberId;

    @Column(unique = true)
    private String loginId;
    private String memberName;

    @Column(unique = true)
    private String email;
    private String password;

    private MemberRole memberRole;

//    @OneToMany(mappedBy = "member")
//    private List<Board> board = new ArrayList<>();

    public Member(String loginId, String memberName, String email, String password, MemberRole memberRole) {
        this.loginId = loginId;
        this.memberName = memberName;
        this.email = email;
        this.password = password;
        this.memberRole = memberRole;
    }
}
