package study.board.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // username 이 loginId 이다.(loginForm.html 참조)
        Member member = this.memberRepository.findByLoginId(username)
                            .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        List<GrantedAuthority> authorities = new ArrayList<>();

        if ("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(MemberRole.MEMBER.getValue()));
        }

        return new User(member.getLoginId(), member.getPassword(), authorities);
    }

    @Transactional
    public Member findMember(String memberName) {
        return memberRepository.findByLoginId(memberName).orElseThrow(IllegalArgumentException::new);
    }
}
