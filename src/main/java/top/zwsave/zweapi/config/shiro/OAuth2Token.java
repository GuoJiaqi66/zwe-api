package top.zwsave.zweapi.config.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @Author: Ja7
 * @Date: 2022-01-12 19:05
 */
public class OAuth2Token implements AuthenticationToken {

    private String token;

    public OAuth2Token(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
