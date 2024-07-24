package study.board.mail;

import java.time.Duration;

public interface EmailAuthentication {
    String getData(String key);
    boolean existData(String key);
    void setDataExpire(String key, String value, long duration);
    void deleteData(String key);
    Duration getDataExpire(String key);
}
