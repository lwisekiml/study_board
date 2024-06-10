package study.board.oauth2;

import java.util.HashMap;
import java.util.Map;

public class KakaoResponse implements OAuth2Response {

    private final Map<String, Object> attribute = new HashMap<String, Object>();

    public KakaoResponse(Map<String, Object> attribute) {
        /*
            {
              id=,
              connected_at=
              properties={
                nickname=
              },
              kakao_account={
                profile_nickname_needs_agreement=,
                profile_image_needs_agreement=,
                profile={
                  nickname=,
                  is_default_nickname=
                },
                has_email=,
                email_needs_agreement=,
                is_email_valid=,
                is_email_verified=,
                email=
              }
            }
         */
        Long id = (Long) attribute.get("id");
        Map<String, Object> account = (Map<String, Object>) attribute.get("kakao_account");
        String email = (String) account.get("email");
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");
        String nickname = (String) profile.get("nickname");

        this.attribute.put("id", Long.toString(id));
        this.attribute.put("nickname", nickname);
        this.attribute.put("email", email);
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
    }

    @Override
    public String getEmail() {
        return attribute.get("email").toString();
    }

    @Override
    public String getName() {
        // 카카오는 이름을 받아오려면 비즈앱 신청을 해야 받을 수 있어서 부득이 하게 nickanme으로 함
        // 이 값이 ID가 되어서 kakao client Id로 설정 함(이 값이 oauth2_authorized_client 테이블에 principal_name이 됨)
        return attribute.get("id").toString();
    }
}
