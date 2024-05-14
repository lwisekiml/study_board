package study.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.board.dto.BoardFormDto;
import study.board.entity.Board;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("select new study.board.dto.BoardFormDto(b.id, b.title, b.views) from Board b where b.loginId = :loginId")
    List<BoardFormDto> findMemberDto(@Param("loginId") String loginId);

}
