//package study.board.repository;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//import study.board.member.MemberRepository;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@Transactional
//@Rollback(value = false)
//class MemberRepositoryTest {
//
//    @Autowired
//    MemberRepository memberRepository;
//
////    @Test
////    public void testMember() {
////        Member member = new Member("memberA");
////        Member saveMember = memberRepository.save(member);
////
////        Member findMember = memberRepository.findById(saveMember.getId()).get();
////
////        assertThat(findMember.getId()).isEqualTo(member.getId());
////        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
////        assertThat(findMember).isEqualTo(member);
////    }
//}
