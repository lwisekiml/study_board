package study.board.testenti;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberTest {

    @PersistenceContext EntityManager em;
    @Autowired
    private ChildRepository childRepository;

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

    @Test
    public void 지연로딩() {
        Team team = new Team();
        team.setName("TeamA");
        em.persist(team);

        Member member = new Member();
        member.setUsername("member1");
        member.setTeam(team);
        em.persist(member);

        em.flush();
        em.clear();

//        Member m = em.find(Member.class, member.getId());
//        System.out.println("m.getTeam().getClass() = " + m.getTeam().getClass());
//
//        System.out.println("=====================");
//        m.getTeam(); // Proxy를 가져와 쿼리x
//        System.out.println("=====================");
//        m.getTeam().getName(); // 실제 team을 사용, 초기화(DB 조회)
//        System.out.println("=====================");


        // 1
        // SQL : select * from Member
//        Member member1 = em.find(Member.class, member.getId());

        // 2
        // SQL : select * from Member
        List<Member> members = em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    @Test
    public void 즉시로딩() {
        Team teamA = new Team();
        teamA.setName("teamA");
        em.persist(teamA);

        Team teamB = new Team();
        teamB.setName("teamA");
        em.persist(teamB);

        Member member1 = new Member(); // @ManyToOne(fetch = FetchType.EAGER) 로 바꾸고 테스트
        member1.setUsername("member1");
        member1.setTeam(teamA);
        em.persist(member1);

        Member member2 = new Member();
        member2.setUsername("member2");
        member2.setTeam(teamB);
        em.persist(member2);

        em.flush();
        em.clear();

        // 1
        // join을 통해 team도 가져온다.
//        Member member = em.find(Member.class, member1.getId());

        // 2
        // SQL : select * from Member
        // SQL : select * from Team where TEAM_ID = xxx
        // SQL : select * from Team where TEAM_ID = xxx
        List<Member> members = em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

//    @Test
//    public void 영속성전이() {
//        Child child1 = new Child();
//        Child child2 = new Child();
//
//        Parent parent = new Parent();
//        parent.addChild(child1);
//        parent.addChild(child2);
//
//        // cascade = CascadeType.ALL로 아래 2개는 안해도 된다.
//        // cascade = CascadeType.ALL이 없으면 아래 2개 주석을 풀고 해야 한다.
//        em.persist(parent);
////        em.persist(child1);
////        em.persist(child2);
//    }

    @Test
    public void 고아객체1_OneToMany() {
//        Child child1 = new Child();
//        Child child2 = new Child();
//
//        Parent parent = new Parent();
//        parent.addChild(child1);
//        parent.addChild(child2);
//
//        em.persist(parent);
//
//        em.flush();
//        em.clear();
//
//        Parent findParent = em.find(Parent.class, parent.getId());
//        em.remove(findParent); // 부모 delete 하면 child도 delete 됨
////        findParent.getChildList().remove(0); // child 1개 delete
    }

    @Test
    public void 고아객체2_OneToOne() {
        Parent parent = new Parent();
        Child child = new Child();

        // 정상 (cascade 없을때 persist 순서를 child -> parent로 하면 3번 쿼리나감 / insert child -> insert parent -> update child)
        parent.addChild(child);
        em.persist(parent);
//        em.persist(child);

        // cascade 없을때 에러 발생
//        parent.addChild(child);
//        em.persist(parent);


        em.flush();
        em.clear();

        Parent findParent = em.find(Parent.class, parent.getId());
        System.out.println("=======================");
        Child child1 = findParent.getChild();
        findParent.setChild(null);
        em.remove(child1);
//        em.remove(findParent.getChild());
        System.out.println("=======================");
//        childRepository.delete(findParent.getChild());
//        childRepository.delete(child1);
        System.out.println("=======================");
    }
}