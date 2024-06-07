package study.board.kakao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class KakaoController {

    private final KakaoService kakaoService;

    @GetMapping("/kakao/login")
    public String login() {
        return kakaoService.getAuthorization();
    }

    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code) {
        // 인가 코드 받기
        // @RequestParam("code") String code

        // 토큰 받기
        String token = kakaoService.getToken(code);

        // 이 사이트에 회원 가입이 되어 있는지 확인

        // 가입이 되어 있다면 리스트 페이지로 이동

        // 안되어 있다면 회원가입 페이지로 이동

        return "member/signup";
    }

}
