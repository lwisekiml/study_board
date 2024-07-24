package study.board;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.board.board.Board;
import study.board.mail.EmailAuthentication;
import study.board.mail.VerificationCodeRepository;
import study.board.util.RedisUtil;

@Configuration
public class AppConfig {

    @Bean
    public EmailAuthentication emailAuthentication() {
        return new VerificationCodeRepository();
        // docker 사용할 때
//        return new RedisUtil();
    }
}
