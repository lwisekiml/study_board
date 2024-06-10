//package study.board.kakao;
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import java.util.Date;
//import java.util.HashMap;
//
//@Getter
//@NoArgsConstructor
//@JsonIgnoreProperties(ignoreUnknown = true) // Json 파싱시 없는 필드 무시
//public class KakaoUserInfoResponseDto {
//
//    // 회원 번호
//    @JsonProperty("id")
//    public Long id;
//
//    // 자동 연결 설정을 비활성화한 경우만 존재
//    // 연결하기 호출의 완료 여부
//    // false: 연결 대기(Preregistered) 상태
//    // true: 연결(Registered) 상태
//    @JsonProperty("has_signed_up")
//    public Boolean hasSignedUp;
//
//    // 서비스에 연결 완료된 시각, UTC(한국 시간(KST)과 9시간 차이)
//    @JsonProperty("connected_at")
//    public Date connectedAt;
//
//    // 카카오싱크 간편가입을 통해 로그인한 시각, UTC
//    @JsonProperty("synched_at")
//    public Date synchedAt;
//
//    // 사용자 프로퍼티
//    @JsonProperty("properties")
//    public HashMap<String, String> properties;
//
//    // 카카오 계정 정보
//    @JsonProperty("kakao_account")
//    public KakaoAccount kakaoAccount;
//
//    // uuid 등 추가 정보
//    @JsonProperty("for_partner")
//    public Partner partner;
//
//    @Getter
//    @NoArgsConstructor
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class KakaoAccount {
//
//        // 사용자 동의 시 프로필 정보(닉네임/프로필 사진) 제공 가능
//        @JsonProperty("profile_needs_agreement")
//        public Boolean isProfileAgree;
//
//        // 사용자 동의 시 닉네임 제공 가능
//        @JsonProperty("profile_nickname_needs_agreement")
//        public Boolean isNickNameAgree;
//
//        // 사용자 동의 시 프로필 사진 제공 가능
//        @JsonProperty("profile_image_needs_agreement")
//        public Boolean isProfileImageAgree;
//
//        // 프로필 정보
//        @JsonProperty("profile")
//        public Profile profile;
//
//        // 사용자 동의 시 카카오계정 이름 제공 가능
//        @JsonProperty("name_needs_agreement")
//        public Boolean isNameAgree;
//
//        // 카카오계정 이름
//        @JsonProperty("name")
//        public String name;
//
//        // 사용자 동의 시 카카오계정 대표 이메일 제공 가능
//        @JsonProperty("email_needs_agreement")
//        public Boolean isEmailAgree;
//
//        // 이메일 유효 여부
//        // true : 유효한 이메일
//        // false : 이메일이 다른 카카오 계정에 사용돼 만료
//        @JsonProperty("is_email_valid")
//        public Boolean isEmailValid;
//
//        // 이메일 인증 여부
//        // true : 인증된 이메일
//        // false : 인증되지 않은 이메일
//        @JsonProperty("is_email_verified")
//        public Boolean isEmailVerified;
//
//        //카카오계정 대표 이메일
//        @JsonProperty("email")
//        public String email;
//
//        // 사용자 동의 시 연령대 제공 가능
//        @JsonProperty("age_range_needs_agreement")
//        public Boolean isAgeAgree;
//
//        //연령대
//        /*
//            1~9: 1세 이상 10세 미만
//            10~14: 10세 이상 15세 미만
//            15~19: 15세 이상 20세 미만
//            20~29: 20세 이상 30세 미만
//            30~39: 30세 이상 40세 미만
//            40~49: 40세 이상 50세 미만
//            50~59: 50세 이상 60세 미만
//            60~69: 60세 이상 70세 미만
//            70~79: 70세 이상 80세 미만
//            80~89: 80세 이상 90세 미만
//            90~: 90세 이상
//         */
//        @JsonProperty("age_range")
//        public String ageRange;
//
//        // 사용자 동의 시 출생 연도 제공 가능
//        @JsonProperty("birthyear_needs_agreement")
//        public Boolean isBirthYearAgree;
//
//        // 출생 연도(YYYY 형식)
//        @JsonProperty("birthyear")
//        public String birthYear;
//
//        // 사용자 동의 시 생일 제공 가능
//        @JsonProperty("birthday_needs_agreement")
//        public Boolean isBirthDayAgree;
//
//        // 생일(MMDD 형식)
//        @JsonProperty("birthday")
//        public String birthDay;
//
//        // 생일 타입
//        // SOLAR(양력) 혹은 LUNAR(음력)
//        @JsonProperty("birthday_type")
//        public String birthDayType;
//
//        // 사용자 동의 시 성별 제공 가능
//        @JsonProperty("gender_needs_agreement")
//        public Boolean isGenderAgree;
//
//        // 성별
//        @JsonProperty("gender")
//        public String gender;
//
//        // 사용자 동의 시 전화번호 제공 가능
//        @JsonProperty("phone_number_needs_agreement")
//        public Boolean isPhoneNumberAgree;
//
//        // 카카오계정의 전화번호
//        // 국내 번호인 경우 +82 00-0000-0000 형식
//        @JsonProperty("phone_number")
//        public String phoneNumber;
//
//        // 사용자 동의 시 CI 참고 가능
//        @JsonProperty("ci_needs_agreement")
//        public Boolean isCIAgree;
//
//        // 연계 정보
//        @JsonProperty("ci")
//        public String ci;
//
//        // CI 발급 시각, UTC
//        @JsonProperty("ci_authenticated_at")
//        public Date ciAuthenticatedAt;
//
//        @Getter
//        @NoArgsConstructor
//        @JsonIgnoreProperties(ignoreUnknown = true)
//        public class Profile {
//
//            // 닉네임
//            @JsonProperty("nickname")
//            public String nickName;
//
//            // 프로필 미리보기 이미지 URL
//            // 110px * 110px 또는 100px * 100px
//            @JsonProperty("thumbnail_image_url")
//            public String thumbnailImageUrl;
//
//            // 프로필 사진 URL
//            // 640px * 640px 또는 480px * 480px
//            @JsonProperty("profile_image_url")
//            public String profileImageUrl;
//
//            // 프로필 사진 URL이 기본 프로필 사진 URL인지 여부
//            // 사용자가 등록한 프로필 사진이 없을 경우, 기본 프로필 사진 제공
//            // true: 기본 프로필 사진
//            // false: 사용자가 등록한 프로필 사진
//            @JsonProperty("is_default_image")
//            public String isDefaultImage;
//
//            // 닉네임이 기본 닉네임인지 여부
//            // 사용자가 등록한 닉네임이 운영정책에 부합하지 않는 경우, "닉네임을 등록해주세요"가 기본 닉네임으로 적용됨
//            // true: 기본 닉네임
//            // false: 사용자가 등록한 닉네임
//            @JsonProperty("is_default_nickname")
//            public Boolean isDefaultNickName;
//        }
//    }
//
//    @Getter
//    @NoArgsConstructor
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class Partner {
//        //고유 ID
//        // 카카오톡 메시지 API 사용 권한이 있는 경우에만 제공
//        @JsonProperty("uuid")
//        public String uuid;
//    }
//}
