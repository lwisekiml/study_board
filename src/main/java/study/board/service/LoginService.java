package study.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.board.dto.LoginFormDto;
import study.board.entity.Member;
import study.board.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public LoginFormDto login(String loginId, String password) {

        Member loginMember = memberRepository.findByLoginIdAndPassword(loginId, password);
        if (loginMember == null) {
            return null;
        }
        return new LoginFormDto(loginMember.getId(), loginMember.getLoginId(), loginMember.getPassword());
    }
}
