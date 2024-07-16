package study.board.mail;

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

    public String getVerificationCodeExpiredAt() {
        return getCreateAt()
                .plusMinutes(expirationTimeInMinutes)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
