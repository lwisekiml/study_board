package study.board.mail;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/verify-email")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest.EmailForVerificationRequest request) throws MessagingException, UnsupportedEncodingException {
        emailService.sendEmail(request.getEmail());
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/verification-code")
    public ResponseEntity<String> verifyCode(@RequestBody EmailRequest.VerificationRequest verificationRequest) {
        if (emailService.verifyCode(verificationRequest)) {
            return ResponseEntity.ok("ok");
        }
        return ResponseEntity.notFound().build();
    }
}
