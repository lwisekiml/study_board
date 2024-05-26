package study.board.testenti;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Parent {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    /**
     * 참고 : https://tecoble.techcourse.co.kr/post/2021-08-15-jpa-cascadetype-remove-vs-orphanremoval-true
     * 부모 엔티티 삭제
     *  CascadeType.REMOVE와 orphanRemoval = true는 부모 엔티티를 삭제하면 자식 엔티티도 삭제한다.
     *
     * 부모 엔티티에서 자식 엔티티 제거
     *  CascadeType.REMOVE는 자식 엔티티가 그대로 남아있는 반면, orphanRemoval = true는 자식 엔티티를 제거한다.
     */
    // 테스트 : 고아객체1_OneToMany
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Child> childList = new ArrayList<>();

    public void addChild(Child child) {
        childList.add(child);
        child.setParent(this);
    }

    // 테스트 : 고아객체2_OneToOne
//    @OneToOne(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
//    private Child child;
//
//    public void addChild(Child child) {
//        this.child = child;
//        child.setParent(this);
//    }
}
