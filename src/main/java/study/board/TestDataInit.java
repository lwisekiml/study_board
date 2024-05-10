package study.board;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import study.board.entity.Member;
import study.board.entity.Post;
import study.board.repository.MemberRepository;
import study.board.repository.PostRepository;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    /*
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        memberRepository.save(new Member("test", "kim", "1234"));
        memberRepository.save(new Member("qwer", "lee", "zxcv"));

        postRepository.save(new Post("test", "제목1", "내용1"));
        postRepository.save(new Post("qwer", "제목2", "내용2"));
    }
}
