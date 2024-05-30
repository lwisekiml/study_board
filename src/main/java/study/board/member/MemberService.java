package study.board.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import study.board.member.Member;
import study.board.member.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void create(MemberDto memberDto) {


        // 비밀 번호 암호화
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));

        // 저장
        memberRepository.save(
                new Member(
                        memberDto.getLoginId(),
                        memberDto.getMemberName(),
                        memberDto.getEmail(),
                        memberDto.getPassword()
                )
        );
    }

}
