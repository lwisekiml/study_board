package study.board.mail;

import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class VerificationCodeRepository implements EmailAuthentication {

    private final Map<String, VerificationCode> repository = new ConcurrentHashMap<>();

    @Override
    public String getData(String key) {
        long seconds = getDataExpire(key).toSeconds();
        if (seconds > 0) {
            return repository.get(key).getCode();
        }
        return null;
    }

    @Override
    public boolean existData(String key) {
        return repository.get(key) != null;
    }

    @Override
    public void setDataExpire(String key, String value, long duration) {
        Duration expireDuration = Duration.ofSeconds(duration);
        repository.put(key, new VerificationCode(value, expireDuration));
    }

    @Override
    public void deleteData(String key) {
        repository.remove(key);
    }

    @Override
    public Duration getDataExpire(String key) {
        LocalDateTime expirationTime = LocalDateTime.parse(repository.get(key).getExpirationTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return Duration.between(LocalDateTime.now(), expirationTime);
    }
}
