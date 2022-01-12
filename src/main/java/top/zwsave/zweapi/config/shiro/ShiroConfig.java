package top.zwsave.zweapi.config.shiro;


import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.zwsave.zweapi.config.shiro.OAuth2Filter;
import top.zwsave.zweapi.config.shiro.OAuth2Realm;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/*
 * 5
 * */
@Configuration
public class ShiroConfig {


    /*
     * 封装OAuth2Realm类，  OAuth2Realm添加了@Component注解
     * */
    @Bean
    public SecurityManager securityManager (OAuth2Realm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        securityManager.setRememberMeManager(null);  // 查资料
        return securityManager;
    }

    /*
     * 封装OAuth2Filter类
     * */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager, OAuth2Filter filter) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);

        Map<String, Filter> map = new HashMap<>();
        map.put("oauth2", filter); // 把自定义的过滤器放到ShiroFilterFactoryBean中
        shiroFilter.setFilters(map);

        // 存放那些需要拦截，哪些不需要拦截 anon不拦截
        Map<String, String> filterMap = new LinkedHashMap<>();

        filterMap.put("/swagger-ui.html", "anon");
        filterMap.put("/swagger-resources/**", "anon");
        filterMap.put("/test", "oauth2");
        shiroFilter.setFilterChainDefinitionMap(filterMap);

        return shiroFilter;

    }


    @Bean // 默认用对象名首字母小写 作为bean名
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /*
    aop
    * web方法执行前，验证权限
    * */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        // 往aop方法中保存setSecurityManager
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
