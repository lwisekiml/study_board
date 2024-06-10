//package study.board.kakao;
//
//import jakarta.persistence.*;
//import lombok.*;
//import study.board.member.Member;
//
//import static jakarta.persistence.FetchType.LAZY;
//
//@Entity
//@Getter @Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor
//public class Kakao {
//
//    @Id @GeneratedValue
//    Long id;
//
//    @Column(unique = true)
//    private String kakaoId;
//
//    @Column(unique = true)
//    private String kakaoEmail;
//
//    @OneToOne(fetch = LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member;
//
//    public Kakao(String kakaoId, String kakaoEmail, Member member) {
//        this.kakaoId = kakaoId;
//        this.kakaoEmail = kakaoEmail;
//        this.member = member;
//    }
//}
