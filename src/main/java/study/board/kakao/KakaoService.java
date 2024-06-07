package study.board.kakao;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

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

    @Transactional
    public String getToken(String code) {

        String result_txt = "";

        try {
//            // 1. HttpURLConnection을 사용하는 방법
//
//            // URL 객체 생성
//            URL url = new URL("https://kauth.kakao.com/oauth/token");
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//
//            // POST 메소드와 Content-Type 설정
//            conn.setRequestMethod("POST");
//            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
//
//            // Request Body에 전송할 데이터
//            String urlParameters = "grant_type=authorization_code" +
//                    "&client_id=" + clientId +
//                    "&redirect_uri=" + redirectUri +
//                    "&code=" + code;
//
//            // POST 요청을 위해 setDoOutput을 true로 설정
//            conn.setDoOutput(true);
//
//            // 데이터 전송
//            try(OutputStream os = conn.getOutputStream()) {
//                byte[] input = urlParameters.getBytes(StandardCharsets.UTF_8);
//                os.write(input, 0, input.length);
//            }
//
//            // 응답 코드와 메시지 출력
//            System.out.println("Response Code: " + conn.getResponseCode());
//            System.out.println("Response Message: " + conn.getResponseMessage());
//
//            // 응답 데이터 읽기
//            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
//            String inputLine;
//            StringBuilder response = new StringBuilder();
//            while ((inputLine = in.readLine()) != null) {
//                response.append(inputLine);
//            }
//            in.close();
//
//            // 응답 출력
//            System.out.println("Response: " + response.toString());
//            String res = response.toString();
//
//            // 연결 종료
//            conn.disconnect();

            //////////////////////////////////////////////////////////////////////////////////////////////////////
//            // 2. RestTemplate 방식
//
//            // RestTemplate 인스턴스 생성
//            RestTemplate restTemplate = new RestTemplate();
//
//            // HttpHeaders 객체를 생성하고 Content-Type 설정
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//            headers.add("Accept-Charset", "utf-8");
//
//            // 요청 본문(body)에 포함될 파라미터 설정
//            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//            params.add("grant_type", "authorization_code");
//            params.add("client_id", clientId);
//            params.add("redirect_uri", redirectUri);
//            params.add("code", code);
//
//            // HttpEntity 객체 생성하여 headers와 params 설정
//            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
//
//            // API 호출
//            String url = "https://kauth.kakao.com/oauth/token";
//            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
//
//            // 응답 출력
//            System.out.println(response.getBody());
//            String res = response.getBody();

            //////////////////////////////////////////////////////////////////////////////////////////////////////

            // 3. webflux

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

            String res = mono.block();
            //////////////////////////////////////////////////////////////////////////////////////////////////////
            ObjectMapper objectMapper = new ObjectMapper();
            KakaoTokenResponseDto kakaoTokenResponseDto = objectMapper.readValue(res, KakaoTokenResponseDto.class);

            return kakaoTokenResponseDto.getAccessToken();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result_txt;
    }
}
