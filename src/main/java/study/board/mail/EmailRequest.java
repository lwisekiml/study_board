package study.board.mail;

import lombok.Getter;

public class EmailRequest {
    @Getter
    public static class EmailForVerificationRequest {
        private String email;
    }

    @Getter
    public static class VerificationRequest {
        private String email;
        private String code;
    }
}
