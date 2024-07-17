package study.board.mail;

import com.nimbusds.oauth2.sdk.GeneralException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/verify-email")
    public String getEmailForVerification(@RequestBody EmailRequest.EmailForVerificationRequest request) throws MessagingException, UnsupportedEncodingException {
        emailService.sendVerificationMail(request.getEmail());
        return "ok";
    }

//    @PostMapping("/verification-code")
//    public String verificationByCode(@RequestBody EmailRequest.VerificationCodeRequest request) throws GeneralException {
//        LocalDateTime requestedAt = LocalDateTime.now();
//        emailService.verifyCode(request.getCode(), requestedAt);
//        return "ok";
//    }
}
