package study.board.mail;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/verify-email")
    public String getEmailForVerification(@RequestBody EmailRequest.EmailForVerificationRequest request) throws MessagingException, UnsupportedEncodingException {
        emailService.sendVerificationMail(request.getEmail());
        return "ok";
    }

    @PostMapping("/verification-code")
    public String verificationByCode(@RequestBody EmailRequest.VerificationRequest verificationRequest) {
        if (emailService.verifyCode(verificationRequest)) {
            return "ok";
        }
        return "error";
    }
}
