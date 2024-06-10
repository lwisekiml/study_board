//package study.board.kakao;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import study.board.member.Member;
//import study.board.member.MemberRepository;
//import study.board.member.MemberService;
//
//@Slf4j
//@Controller
//@RequiredArgsConstructor
//public class KakaoController {
//
//    private final KakaoService kakaoService;
//    private final KakaoRepository kakaoRepository;
//
//    private final MemberService memberService;
//    private final MemberRepository memberRepository;
//
//    private final PasswordEncoder passwordEncoder;
//
//    @GetMapping("/kakao/login")
//    public String login() {
//        return kakaoService.getAuthorization();
//    }
//
//    @GetMapping("/callback")
//    public String callback(@RequestParam("code") String code) {
//        // 인가 코드 받기
//        // @RequestParam("code") String code
//
//        // 토큰 받기
//        String token = kakaoService.getToken(code);
//
//        // kakao 사용자 정보 가져오기
//        KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(token);
//
//        // 이 사이트에 회원 가입이 되어 있는지 확인
//        // kakao에 kakao_id와 일치한 user가 있는지 확인
//        String kakaoUserId = Long.toString(userInfo.getId());
//        String kakaoEmail = userInfo.getKakaoAccount().getEmail();
//        String kakaoNickName = userInfo.getKakaoAccount().getProfile().getNickName();
//
//        // kakao_id와 같은 엔티티를 찾는다.
//        Kakao findKakao = kakaoService.getKakao(kakaoUserId);
//
//        // 없다면 Member 등록 -> kakao 등록 -> 로그인 처리 -> 리스트 페이지로 이동
//        // 있다면 사이트에 가입을 한것 이므로 로그인 처리 -> 리스트 페이지로 이동
//        if (findKakao == null) {
//            Member saveMember = memberRepository.save(new Member(kakaoUserId, kakaoNickName, kakaoEmail, passwordEncoder.encode(kakaoEmail)));
//            kakaoRepository.save(new Kakao(kakaoUserId, kakaoEmail, saveMember));
//        }
//
//        // 로그인 처리
//
//        return "redirect:/";
//    }
//
//}
