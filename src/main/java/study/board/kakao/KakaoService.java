package study.board.kakao;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoService {

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

    @Transactional
    public String getToken(String code) {

        KakaoTokenResponseDto kakaoTokenResponseDto = null;

        try {
            // API 엔드포인트 URL 정의
            String apiUrl = "https://kauth.kakao.com/oauth/token";

            // WebClient 생성
//            WebClient webClient = WebClient.create();

            // 요청 바디 파라미터 정의
            String grantType = "authorization_code";

            // 요청 바디를 Mono로 생성
            Mono<String> requestBody = Mono.just("grant_type=" + grantType +
                    "&client_id=" + clientId +
                    "&redirect_uri=" + redirectUri +
                    "&code=" + code);

            // API에 POST 요청 보내기
            Mono<String> mono = WebClient.create()
                    .post()
                    .uri(apiUrl)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromPublisher(requestBody, String.class))
                    .retrieve()
                    .bodyToMono(String.class);

            String response = mono.block();
            ObjectMapper objectMapper = new ObjectMapper();
            kakaoTokenResponseDto = objectMapper.readValue(response, KakaoTokenResponseDto.class);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return kakaoTokenResponseDto.getAccessToken();
    }
}
