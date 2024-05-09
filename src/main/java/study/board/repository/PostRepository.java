package study.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.board.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
