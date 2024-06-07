package study.board.kakao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoService {

    private final KakaoRepository kakaoRepository;

    @Value("${kakao.rest_api.client_id}")
    private String clientId;

    @Value("${kakao.redirect_uri}")
    private String redirectUri;

    public String getAuthorization() {
        // clientId : rest api
        // redirectUri : kakao에서 이 주소로 인가 코드를 준다.
        return "redirect:https://kauth.kakao.com/oauth/authorize?response_type=code" +
                "&client_id=" + clientId + "&redirect_uri=" + redirectUri;
    }
}
