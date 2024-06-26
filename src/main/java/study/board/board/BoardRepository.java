package study.board.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

//    @Query("select new study.board.dto.BoardDto(b.id, b.title, b.views) from Board b where b.loginId = :loginId")
//    List<BoardDto> findMemberDto(@Param("loginId") String loginId);
    List<Board> findByTitleContaining(String search);
}
