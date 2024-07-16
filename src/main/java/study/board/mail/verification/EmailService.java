package study.board.mail.verification;

import com.nimbusds.oauth2.sdk.GeneralException;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {
    @Value("${spring.mail.username")
    private String from;
    private String sender = "Board_Master";
    private final int VERIFICATION_CODE_LENGTH = 6;
    private final Integer EXPIRATION_TIME_IN_MINUTES = 5;

    @Qualifier("javaMailSender")
    private final JavaMailSender mailSender;
    private final VerificationCodeRepository verificationCodeRepository;

    public void sendVerificationMail(String to) throws MessagingException, UnsupportedEncodingException {
        VerificationCode verificationCode = generateVerificationCode();
        verificationCodeRepository.save(verificationCode);

        MimeMessage mailMessage = generateCodeMessage(verificationCode);
        mailMessage.setFrom(new InternetAddress(from, sender));
        mailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

        mailSender.send(mailMessage);
    }

    private VerificationCode generateVerificationCode() {
        Random random = new Random();
        StringBuffer code = new StringBuffer();

        for(int i = 0; i < VERIFICATION_CODE_LENGTH; i++) {
            int index = random.nextInt(3);

            switch (index) {
                case 0 :
                    code.append((char) (random.nextInt(26) + 97));
                    break; // a~z  (ex. 1 + 97 = 98 => (char)98 = 'b')
                case 1:
                    code.append((char) (random.nextInt(26) + 65));
                    break; // A ~ Z
                case 2:
                    code.append(random.nextInt(9));
                    break; // 0 ~ 9
            }
        }

        return VerificationCode.builder()
                .code(code.toString())
                .createAt(LocalDateTime.now())
                .expirationTimeInMinutes(EXPIRATION_TIME_IN_MINUTES)
                .build();
    }

    public MimeMessage generateCodeMessage(VerificationCode verificationCode) throws MessagingException {
        String verificationCodeExpiredAt = verificationCode.getVerificationCodeExpiredAt();
        String msgOfEmail = "<div style='margin:20px;'>" +
                "<h1>이메일 인증코드</h1><br/>" +
                "<p>아래 코드를 입력해주세요.<p><br/>" +
                "<div align='center' style='border:1px solid black; font-family:verdana;'>" +
                "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>" +
                "<div style='font-size:130%'>" +
                "CODE : <strong>" + verificationCode.getCode() + "</strong><br/>" +
                "만료일 : " + verificationCodeExpiredAt +
                "</div><br/> " +
                "</div><br/>" +
                "<p>감사합니다.<p>" +
                "<br/>";

        String title = " 회원가입을 위한 인증 코드입니다.";
        MimeMessage message = mailSender.createMimeMessage();
        message.setSubject(title);
        message.setText(msgOfEmail, "utf-8", "html");

        return message;
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
