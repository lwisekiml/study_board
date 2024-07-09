package study.board.mail;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;

@Controller
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @ResponseBody
    @PostMapping("/sign-up/emailCheck")
    public String emailCheck(@RequestBody EmailCheckReq emailCheckReq) throws MessagingException, UnsupportedEncodingException {
        emailService.sendEmail(emailCheckReq.getEmail());
        return "ok";
    }
}
