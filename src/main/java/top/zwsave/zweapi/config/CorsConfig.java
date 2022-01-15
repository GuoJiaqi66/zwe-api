package top.zwsave.zweapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
*
* 解决跨域
* */

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //针对所接口 ebook
                .allowedOriginPatterns("*")  // 允许来源
                .allowedHeaders(CorsConfiguration.ALL) //
                .allowedMethods(CorsConfiguration.ALL) // get post delete
                .allowCredentials(true) // 可以携带凭证
                .maxAge(3600); // 1小时内不需要再预检（发OPTIONS请求）

    }
}
