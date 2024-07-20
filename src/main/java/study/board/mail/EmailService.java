package study.board.mail;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import study.board.util.RedisUtil;

import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final RedisUtil redisUtil;
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username")
    private String from;
    private String sender = "Board_Master";

    private final int VERIFICATION_CODE_LENGTH = 6;
    private final Long EXPIRATION_TIME_IN_MINUTES = 60*3L; // 3분

    public void sendEmail(String toEmail) throws MessagingException, UnsupportedEncodingException {
        if (redisUtil.existData(toEmail)) {
            redisUtil.deleteData(toEmail);
        }

        MimeMessage emailForm = createEmailForm(toEmail);
        mailSender.send(emailForm);
    }

    public MimeMessage createEmailForm(String toEmail) throws MessagingException, UnsupportedEncodingException {
        VerificationCode verificationCode = setVerificationCode(toEmail);

        MimeMessage message = mailSender.createMimeMessage();
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        message.setSubject("회원가입을 위한 인증 코드입니다.");
        message.setFrom(new InternetAddress(from, sender));
        message.setText(setContext(verificationCode), "utf-8", "html");

        return message;
    }

    private VerificationCode setVerificationCode(String toEmail) {
        String code = createCode();
        redisUtil.setDataExpire(toEmail, code, EXPIRATION_TIME_IN_MINUTES);

        Duration dataExpire = redisUtil.getDataExpire(toEmail);
        return new VerificationCode(code, dataExpire);
    }

    private String createCode() {
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

        return code.toString();
    }

    private String setContext(VerificationCode verificationCode) {
        // TODO : 아래 코드로 바꾸는 것이 더 좋을 것으로 판단되어 바꾸려 했지만
        //        templateEngine.process("member/mailForm", context); 에서 에러 발생하여 보류
//        Context context = new Context();
//        TemplateEngine templateEngine = new TemplateEngine();
//        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
//
//        context.setVariable("code", verificationCode.getCode());
//        context.setVariable("expiration", verificationCode.getExpirationTime());
//
//        templateResolver.setPrefix("templates/");
//        templateResolver.setSuffix(".html");
//        templateResolver.setTemplateMode(TemplateMode.HTML);
//        templateResolver.setCacheable(false);
//
//        templateEngine.setTemplateResolver(templateResolver);
//
//        String str =  templateEngine.process("member/mailForm", context);
//        return str;

        return "<div style='margin:20px;'>" +
                "<h1>이메일 인증코드</h1><br/>" +
                "<p>아래 코드를 입력해주세요.<p><br/>" +
                "<div align='center' style='border:1px solid black; font-family:verdana;'>" +
                "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>" +
                "<div style='font-size:130%'>" +
                "CODE : <strong>" + verificationCode.getCode() + "</strong><br/>" +
                "만료일 : " + verificationCode.getExpirationTime() +
                "</div><br/> " +
                "</div><br/>" +
                "<p>감사합니다.<p>" +
                "<br/>";
    }

    public Boolean verifyCode(EmailRequest.VerificationRequest verificationRequest) {
        String code = redisUtil.getData(verificationRequest.getEmail());
        if (verificationRequest.getCode().equals(code)) {
            redisUtil.deleteData(verificationRequest.getEmail());
            return true;
        }
        return false;
    }
}
