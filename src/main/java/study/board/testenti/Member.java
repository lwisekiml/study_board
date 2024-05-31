package study.board.testenti;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(name = "JPAMEMBER")
@Getter @Setter
public class Member extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @ManyToOne(fetch = FetchType.LAZY) // 없으면 한번에 join해서 가져온다.
    @JoinColumn(name = "TEAM_ID") // join해야 되는 컬럼이 뭐냐
    private Team team;

//    @OneToOne
//    @JoinColumn(name = "LOCKER_ID")
//    private Locker locker;


//    public void chageTeam(Team team) {
//        this.team = team;
//        team.getMembers().add(this);
//    }
}


//@Entity
//@Getter @Setter
//public class Member {
//
//    @Id
//    @GeneratedValue
//    @Column(name = "MEMBER_ID")
//    private Long id;
//    private String name;
//    private String city;
//    private String street;
//    private String zipcode;
//
//    @OneToMany(mappedBy = "member")
//    private List<Order> orders = new ArrayList<>();
//}
