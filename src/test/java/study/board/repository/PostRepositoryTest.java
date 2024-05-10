package study.board.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.board.entity.Post;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@Transactional
@Rollback(value = false)
class PostRepositoryTest {

    @Autowired PostRepository postRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    public void testPost() {
        Post post = new Post("member1", "제목", "내용");
        Post savePost = postRepository.save(post);

        Post findPost = postRepository.findById(savePost.getId()).get();

        assertThat(findPost.getId()).isEqualTo(post.getId());
        assertThat(findPost.getLoginId()).isEqualTo(post.getLoginId());
        assertThat(findPost).isEqualTo(post);
    }

}