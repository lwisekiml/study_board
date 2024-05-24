package study.board.testenti;

import jakarta.persistence.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberTest {

    @PersistenceContext EntityManager em;

    @Test
    public void test() {

        Member member = new Member();
        member.setUsername("member1");
//        member.chageTeam(team);
        em.persist(member);

        Team team = new Team();
        team.setName("TeamA");
        team.getMembers().add(member);

        em.persist(team);
//        team.getMembers().add(member);

//        em.flush();
//        em.clear();

//        Team findTeam = em.find(Team.class, team.getId()); // from team
//        List<Member> members = findTeam.getMembers(); // from member
//
//        System.out.println("========================");
//        System.out.println("findTeam = " + findTeam);
//        System.out.println("========================");
        int a = 1;
    }

    @Test
    public void 상속테스트() {
        Movie movie = new Movie();
        movie.setDirector("aaa");
        movie.setActor("bbbb");
        movie.setName("바람과함계사라지다");
        movie.setPrice(10000);

        em.persist(movie); // insert 2번(item, movie)

        em.flush();
        em.clear();

        Item item = em.find(Movie.class, movie.getId());
        System.out.println("item = " + item);

//        Movie findMovie = em.find(Movie.class, movie.getId()); // 조인 결과
//        System.out.println("findMovie = " + findMovie);
    }

    @Test
    public void 실전예제4() {
        Book book = new Book();
        book.setName("JPA");
        book.setAuthor("kim");

        em.persist(book);
    }

    @Test
    public void 프록시() {
        Member member = new Member();
        member.setUsername("hello");

        em.persist(member);

        em.flush();
        em.clear();

        Member findMember = em.getReference(Member.class, member.getId()); // getReference() 호출 시점에는 쿼리 x
        System.out.println("findMember.getClass() = " + findMember.getClass());
        System.out.println("findMember.getId() = " + findMember.getId()); // 위에 member.getId() 한것 때문에 쿼리 x
        System.out.println("findMember.getUsername() = " + findMember.getUsername()); // 쿼리 날림

        System.out.println("findMember.getUsername() = " + findMember.getUsername()); // 쿼리 x
        System.out.println("findMember.getClass() = " + findMember.getClass());
    }
}