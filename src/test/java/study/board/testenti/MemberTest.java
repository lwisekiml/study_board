package study.board.testenti;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

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

    @Test
    public void 영속성_컨텍스트에_찾는_엔티티가_이미_있으면_getReference_를_호출해도_실제_엔티티_반환() {
        Member member1 = new Member();
        member1.setUsername("member1");
        em.persist(member1);

        Member member2 = new Member();
        member2.setUsername("member2");
        em.persist(member2);

        em.flush();
        em.clear();

        // 1. find 와 find 비교
//        Member m1 = em.find(Member.class, member1.getId()); // Member
//        Member m2 = em.find(Member.class, member2.getId()); // Member
//
//        System.out.println("m1 = " + m1.getClass()); // m1 = class hellojpa.Member
//        System.out.println("m2 = " + m2.getClass()); // m2 = class hellojpa.Member
//        System.out.println("m1 == m2 : " + (m1.getClass() == m2.getClass())); // true

        // 2. find 와 getReference 비교
//        Member m3 = em.find(Member.class, member1.getId()); // Member
//        Member m4 = em.getReference(Member.class, member2.getId()); // Proxy
//
//        System.out.println("m3 = " + m3.getClass()); // Member
//        System.out.println("m4 = " + m4.getClass()); // Proxy
//        System.out.println("m3 == m4 : " + (m3.getClass() == m4.getClass())); // false
//        System.out.println("m3 : " + (m3 instanceof Member)); // true
//        System.out.println("m3 : " + (m4 instanceof Member)); // true

        // 3. 영속성 컨텍스트에 찾는 엔티티가 이미 있으면 em.getReference()를 호출해도 실제 엔티티 반환
        // find -> getReference : Member
//        Member m1 = em.find(Member.class, member1.getId()); // Member
//        Member m2 = em.getReference(Member.class, member1.getId()); // Member
//        System.out.println("m1 = " + m1.getClass()); // Member
//        System.out.println("m2 = " + m2.getClass()); // Member
//        System.out.println("m1 == m2 : " + (m1.getClass() == m2.getClass())); // true

        // getReference -> find : Proxy
//        Member m1 = em.getReference(Member.class, member1.getId()); // Proxy
//        Member m2 = em.find(Member.class, member1.getId()); // Proxy
//        System.out.println("m1 = " + m1.getClass()); // Proxy
//        System.out.println("m2 = " + m2.getClass()); // Proxy
//        System.out.println("m1 == m2 : " + (m1.getClass() == m2.getClass())); // true

        // 4. detach
//        Member refMember = em.getReference(Member.class, member1.getId());
//        System.out.println("reference.getClass() = " + refMember.getClass());
//
//        em.detach(refMember);
//        em.clear();
//        em.close();
//
//        System.out.println("refMember.getUsername() = " + refMember.getUsername()); // LazyInitializationException
    }
}