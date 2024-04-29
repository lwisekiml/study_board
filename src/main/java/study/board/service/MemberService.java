package study.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.board.entity.Member;
import study.board.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member addMember(String name, String email, String password) {
        return memberRepository.save(new Member(name, email, password));
    }
}
