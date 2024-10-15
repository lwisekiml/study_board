package study.board;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import study.board.board.BoardRepository;
import study.board.config.RedisConfig;
import study.board.mail.EmailAuthentication;
import study.board.mail.VerificationCodeRepository;
import study.board.member.MemberRepository;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final RedisConfig redisConfig;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public EmailAuthentication emailAuthentication() {
        return new VerificationCodeRepository();
        // docker 사용할 때
//        return new RedisUtil(stringRedisTemplate());
    }

    @Profile("local")
    @Bean
    public TestDataInit testDataInit() {
        return new TestDataInit(memberRepository, boardRepository, passwordEncoder);
    }

    public StringRedisTemplate stringRedisTemplate() {
        return new StringRedisTemplate(redisConfig.redisConnectionFactory());
    }
}
