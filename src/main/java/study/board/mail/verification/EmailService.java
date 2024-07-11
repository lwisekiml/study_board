package study.board.mail.verification;

import com.nimbusds.oauth2.sdk.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailService {
    @Value("${spring.mail.username")
    private String serviceEmail;
    private final Integer EXPIRATION_TIME_IN_MINUTES = 5;

    @Qualifier("javaMailSender")
    private final JavaMailSender mailSender;
    private final VerificationCodeRepository verificationCodeRepository;

    public void sendSimpleVerificationMail(String to, LocalDateTime sentAt) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(serviceEmail);
        mailMessage.setTo(to);
        mailMessage.setSubject(String.format("Email Verification For %s", to));

        VerificationCode verificationCode = generateVerificationCode(sentAt);
        verificationCodeRepository.save(verificationCode);

        String text = verificationCode.generateCodeMessage();
        mailMessage.setText(text);

        mailSender.send(mailMessage);
    }

    private VerificationCode generateVerificationCode(LocalDateTime sentAt) {
        String code = UUID.randomUUID().toString();
        return VerificationCode.builder()
                .code(code)
                .createAt(sentAt)
                .expirationTimeInMinutes(EXPIRATION_TIME_IN_MINUTES)
                .build();
    }

    public void verifyCode(String code, LocalDateTime verifiedAt) throws GeneralException {
        VerificationCode verificationCode = verificationCodeRepository.findByCode(code)
                .orElseThrow(() -> new GeneralException("에러"));

        if (verificationCode.isExpired(verifiedAt)) {
            throw new GeneralException("에러");
        }

        verificationCodeRepository.remove(verificationCode);
    }
}
