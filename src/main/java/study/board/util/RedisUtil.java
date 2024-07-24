package study.board.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import study.board.mail.EmailAuthentication;

import java.time.Duration;

@RequiredArgsConstructor
public class RedisUtil implements EmailAuthentication {

    private final StringRedisTemplate stringRedisTemplate;

    public String getData(String key) {
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    public boolean existData(String key) {
        return Boolean.TRUE.equals(stringRedisTemplate.hasKey(key));
    }

    public void setData(String key, String value) {
        ValueOperations<String,String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    public void setDataExpire(String key, String value, long duration) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, value, expireDuration);
    }

    public void deleteData(String key) {
        stringRedisTemplate.delete(key);
    }

    public Duration getDataExpire(String key) {
        Long remainingTimeInSeconds = stringRedisTemplate.getExpire(key);
        if (remainingTimeInSeconds != null && remainingTimeInSeconds > 0) {
            return Duration.ofSeconds(remainingTimeInSeconds);
        }
        return Duration.ZERO; // 키가 없거나 만료된 경우
    }
}
