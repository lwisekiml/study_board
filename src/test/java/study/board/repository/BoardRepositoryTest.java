package study.board.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.board.entity.Board;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@Transactional
@Rollback(value = false)
class BoardRepositoryTest {

    @Autowired
    BoardRepository boardRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    public void testPost() {
        Board board = new Board("member1", "제목", "내용");
        Board saveBoard = boardRepository.save(board);

        Board findBoard = boardRepository.findById(saveBoard.getId()).orElseThrow(IllegalArgumentException::new);

        assertThat(findBoard.getId()).isEqualTo(board.getId());
        assertThat(findBoard.getLoginId()).isEqualTo(board.getLoginId());
        assertThat(findBoard).isEqualTo(board);
    }

}