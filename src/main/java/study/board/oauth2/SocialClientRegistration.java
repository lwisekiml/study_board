//package study.board.oauth2;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.oauth2.client.registration.ClientRegistration;
//import org.springframework.security.oauth2.core.AuthorizationGrantType;
//import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
//import org.springframework.stereotype.Component;
//
//@Component
//public class SocialClientRegistration {
//
//    @Value("${kakao.rest_api.client_id}")
//    private String kakaoClientId;
//
//    @Value("${kakao.rest_api.client-secret}")
//    private String kakaoClientSecret;
//
//    public ClientRegistration kakaoClientRegistration() {
//
//        return ClientRegistration.withRegistrationId("kakao")
//                .clientId(kakaoClientId)
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_POST)
//                .redirectUri("http://localhost:8080/login/oauth2/code/kakao")
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .scope("profile_nickname")
//                .authorizationUri("https://kauth.kakao.com/oauth/authorize")
//                .tokenUri("https://kauth.kakao.com/oauth/token")
//                .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
//                .userInfoUri("https://kapi.kakao.com/v2/user/me")
//                .userNameAttributeName("id")
//                .build();
//    }
//}
