package study.board.mail;

import jakarta.mail.Message.RecipientType;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl2 implements EmailService2 {

    public static final String authCode = createKey();
    private static final int AUTH_CODE_LENGTH = 6;

    @Value("${AdminMail.id}")
    private String fromEmail;
    private String sender = "Board_Master";

    private final JavaMailSender emailSender;

    private MimeMessage createMessage(String to)throws Exception{
        System.out.println("보내는 대상 : "+ to);
        System.out.println("인증 번호 : "+ authCode);
        MimeMessage  message = emailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, to);
        message.setSubject("회원가입을 위한 인증코드 이메일입니다.");

        String msgg="<div style='margin:20px;'>" +
                "<h1>이메일 인증코드</h1><br/>" +
                "<p>아래 코드를 입력해주세요.<p><br/>" +
                "<div align='center' style='border:1px solid black; font-family:verdana;'>" +
                "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>" +
                "<div style='font-size:130%'>" +
                "CODE : <strong>" + authCode + "</strong>" +
                "</div><br/> " +
                "</div><br/>" +
                "<p>감사합니다.<p>" +
                "<br/>";
        message.setFrom(new InternetAddress(fromEmail, sender));
        message.setText(msgg, "utf-8", "html");

        return message;
    }

    public static String createKey() {
        Random random = new Random();
        StringBuffer code = new StringBuffer();

        for(int i = 0; i < AUTH_CODE_LENGTH; i++) {
            int index = random.nextInt(3);

            switch (index) {
                case 0 :
                    code.append((char) (random.nextInt(26) + 97));
                    break; //  a~z  (ex. 1 + 97 = 98 => (char)98 = 'b')
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

    @Override
    public String sendSimpleMessage(String to)throws Exception {
        MimeMessage message = createMessage(to);

        try{
            emailSender.send(message);
        }catch(MailException e){
            log.error("sendEmail error", e);
            throw new IllegalArgumentException();
        }

        return authCode;
    }
}
