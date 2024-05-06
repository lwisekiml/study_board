package study.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.board.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByLoginIdAndPassword(String loginId, String password);
}
