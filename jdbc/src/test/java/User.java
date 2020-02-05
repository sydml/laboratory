import java.time.LocalDateTime;

/**
 * @Author: Liuyuming
 * @Date: 2020/2/5 22:14
 */
public class User {
    Long id;
    String username;
    LocalDateTime loginTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }
}
