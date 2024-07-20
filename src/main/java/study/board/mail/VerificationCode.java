package study.board.mail;

import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
@Builder
public class VerificationCode {
    private String code;
    private String expirationTime;

    public VerificationCode(String code, Duration dataExpire) {
        this.code = code;
        this.expirationTime = toLocalDateTimeString(dataExpire);
    }

    public String toLocalDateTimeString(Duration dataExpire) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiration = now.plus(dataExpire);
        return expiration.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
