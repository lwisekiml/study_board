package study.board.testenti;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Child {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    // 테스트 : 고아객체1_OneToMany
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent parent;

    // 테스트 : 고아객체2_OneToOne
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "parent_id")
//    private Parent parent;
}
