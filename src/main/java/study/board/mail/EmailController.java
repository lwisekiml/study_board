//package study.board.mail;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//@RequiredArgsConstructor
//public class EmailController {
//
//    private final EmailService2 emailService2;
//
//    @PostMapping("/emailConfirm")
//    public String emailConfirm(@RequestParam("email") String email) throws Exception {
//        return emailService2.sendSimpleMessage(email);
//    }
//
////    @ResponseBody
////    @PostMapping("/sign-up/emailCheck")
////    public String emailCheck(@RequestBody EmailCheckReq emailCheckReq) throws MessagingException, UnsupportedEncodingException {
////        emailService.sendEmail(emailCheckReq.getEmail());
////        return "ok";
////    }
//}
