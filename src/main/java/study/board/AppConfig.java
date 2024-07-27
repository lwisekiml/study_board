package study.board;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import study.board.config.RedisConfig;
import study.board.mail.EmailAuthentication;
import study.board.mail.VerificationCodeRepository;
import study.board.util.RedisUtil;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final RedisConfig redisConfig;

    @Bean
    public EmailAuthentication emailAuthentication() {
        return new VerificationCodeRepository();
        // docker 사용할 때
//        return new RedisUtil(stringRedisTemplate());
    }

    public StringRedisTemplate stringRedisTemplate() {
        return new StringRedisTemplate(redisConfig.redisConnectionFactory());
    }
}
