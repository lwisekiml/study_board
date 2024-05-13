package study.board;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import study.board.entity.Board;
import study.board.entity.Member;
import study.board.repository.BoardRepository;
import study.board.repository.MemberRepository;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    /*
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        memberRepository.save(new Member("kim", "test", "1234"));
        memberRepository.save(new Member("lee", "qwer", "zxcv"));

        boardRepository.save(new Board("test", "제목1", "내용1"));
        boardRepository.save(new Board("qwer", "제목2", "내용2"));
    }
}
