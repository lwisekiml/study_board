package study.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.board.dto.PostFormDto;
import study.board.entity.Post;
import study.board.repository.PostRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public void edit(PostFormDto postFormDto) {
        Post post = postRepository.findById(postFormDto.getId()).get();

        post.setTitle(postFormDto.getTitle());
        post.setContent(postFormDto.getContent());
    }
}
