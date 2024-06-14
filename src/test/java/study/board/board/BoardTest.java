//package study.board.board;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//@SpringBootTest
//@Transactional
//public class BoardTest {
//
//    @PersistenceContext EntityManager em;
//    @Autowired BoardRepository boardRepository;
//
//    @Test
//    public void baseTest() throws InterruptedException {
//        Board board = new Board("test", "asdf", 1);
//        boardRepository.save(board); // @PrePersist
//
//        Thread.sleep(100);
//        board.setTitle("member");
//
//        em.flush();
//        em.clear();
//
//        Board findBoard = boardRepository.findById(board.getId()).get();
//
//        System.out.println("findBoard getCreatedDate = " + findBoard.getCreatedDate());
//        System.out.println("findBoard getCreatedBy = " + findBoard.getCreatedBy());
//        System.out.println("findBoard getLastModifiedDate = " + findBoard.getLastModifiedDate());
//        System.out.println("findBoard getLastModifiedBy = " + findBoard.getLastModifiedBy());
//    }
//}
