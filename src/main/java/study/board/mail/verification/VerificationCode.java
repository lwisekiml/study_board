package study.board.mail.verification;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class VerificationCode {
    private String code;
    private LocalDateTime createAt;
    private Integer expirationTimeInMinutes;

    public boolean isExpired(LocalDateTime verifiedAt) {
        LocalDateTime expiredAt = createAt.plusMinutes(expirationTimeInMinutes);
        return verifiedAt.isAfter(expiredAt);
    }

    public String generateCodeMessage() {
        String formattedExpiredAt = createAt
                .plusMinutes(expirationTimeInMinutes)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        return String.format(
                """
                [Verification Code] 
                %s
                Expired At : %s
                """,
                code, formattedExpiredAt
        );
    }
}
