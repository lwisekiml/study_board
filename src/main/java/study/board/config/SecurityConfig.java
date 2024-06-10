package study.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import study.board.oauth2.CustomClientRegistrationRepo;
import study.board.oauth2.CustomOAuth2AuthorizedClientService;
import study.board.oauth2.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity // 모든 요청 URL이 스프링 시큐리티의 제어를 받도록 만드는 annotation(스프링 시큐리티를 활성화)
@EnableMethodSecurity(prePostEnabled = true) // @PreAuthorize 사용을 위함
public class SecurityConfig {

    // oauth2
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomClientRegistrationRepo customClientRegistrationRepo;
    private final CustomOAuth2AuthorizedClientService customOAuth2AuthorizedClientService;
    private final JdbcTemplate jdbcTemplate;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService,
                         CustomClientRegistrationRepo customClientRegistrationRepo,
                         CustomOAuth2AuthorizedClientService customOAuth2AuthorizedClientService,
                         JdbcTemplate jdbcTemplate
    ) {
        this.customOAuth2UserService = customOAuth2UserService;
        this.customClientRegistrationRepo = customClientRegistrationRepo;
        this.customOAuth2AuthorizedClientService = customOAuth2AuthorizedClientService;
        this.jdbcTemplate = jdbcTemplate;
    }

    /*
     * 내부적으로 SecurityFilterChain 클래스가 동작하여 모든 요청 URL에 이 클래스가 필터로 적용되어
     * URL별로 특별한 설정을 할 수 있게 된다. 스프링 시큐리티의 세부 설정은 @Bean 애너테이션을 통해
     * SecurityFilterChain 빈을 생성하여 설정할 수 있다.
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 인증되지 않은 모든 페이지의 요청을 허락한다는 의미(로그인을 하지 않더라도 모든 페이지 접근 가능)
        http
                .csrf(AbstractHttpConfigurer::disable) // csrf 비활성화
//                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                // https://velog.io/@wlgns3855/Spring-Security-%EC%8A%A4%ED%94%84%EB%A7%81-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0%EB%A1%9C-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EA%B8%B0%EB%8A%A5-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0
                .authorizeHttpRequests(request -> request
                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
//                        .requestMatchers("/", "/login", "/member/signup").permitAll()
//                        .anyRequest().authenticated()
//                )
                .formLogin(form -> form
                        .loginPage("/member/login") // 로그인 페이지의 URL
                        .defaultSuccessUrl("/") // .defaultSuccessUrl("/") // 로그인 성공 시에 이동할 페이지
                        .permitAll()
                )

                // oauth2 login
                .oauth2Login((oauth2) -> oauth2
                        .clientRegistrationRepository(customClientRegistrationRepo.clientRegistrationRepository())
                        .authorizedClientService(customOAuth2AuthorizedClientService.oAuth2AuthorizedClientService(jdbcTemplate, customClientRegistrationRepo.clientRegistrationRepository()))
                        .userInfoEndpoint((userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(customOAuth2UserService)))
                )

//                .logout(LogoutConfigurer::permitAll)
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)) // 로그아웃 시 생성된 사용자 세션도 삭제

//                .sessionManagement((session) ->
//                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        ;

//        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
