package top.zwsave.zweapi.config.shiro;

import org.springframework.stereotype.Component;

/**
 * @Author: Ja7
 * @Date: 2022-01-12 18:57
 */
@Component
public class ThreadLocalToken {

    private ThreadLocal<String> local = new ThreadLocal<>();

    public void setToken(String token) {
        local.set(token);
    }

    public String getToken() {
        return local.get();
    }

    public void clear() {
        local.remove();
    }
}
