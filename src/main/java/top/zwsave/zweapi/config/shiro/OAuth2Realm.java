package top.zwsave.zweapi.config.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;
import top.zwsave.zweapi.service.UserService;

import javax.annotation.Resource;

/**
 * @Author: Ja7
 * @Date: 2022-01-12 19:07
 */
@Component
public class OAuth2Realm extends AuthorizingRealm {

    @Resource
    private JwtUtil jwtUtil;

    @Resource
    UserService userService;


    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {


        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(); // 生成认证对象

        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token =(String) authenticationToken.getPrincipal();
        int userId = jwtUtil.getUserId(token);



        return null;
    }
}
