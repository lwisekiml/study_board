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

    private String loginId;
    private String memberName;
    private String email;
    private String password;

//    @OneToMany(mappedBy = "member")
//    private List<Board> board = new ArrayList<>();

    public Member(String loginId, String memberName, String email, String password) {
        this.loginId = loginId;
        this.memberName = memberName;
        this.email = email;
        this.password = password;
    }
}
