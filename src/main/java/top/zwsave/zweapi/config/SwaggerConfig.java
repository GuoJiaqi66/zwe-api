package top.zwsave.zweapi.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Ja7
 * @Date: 2022-01-01 18:28
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);

        ApiInfoBuilder builder = new ApiInfoBuilder();// 创建容器
        // 设置基本信息
        builder.title("zwe-api 接口");
        // builder 不能直接返回给docket对象
        // 使用ApiIifo 封装builder然后返回给docket
        ApiInfo build = builder.build();
        docket.apiInfo(build); // ApiInfo 传给Docket对象， swagger标题就有了




        // 什么包下的什么类添加到swagger中
        ApiSelectorBuilder selectorBuilder = docket.select(); // 创建对象
        selectorBuilder.paths(PathSelectors.any()); // 所有包下的所有类添加到swagger中
        // 类中的特定方法才能添加到swagger中
        selectorBuilder.apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class)); // 添加了@ApiOperation注解才会添加到swagger中
        docket = selectorBuilder.build();




        // swagger 支持jwt
        // 告诉swagger 令牌，参数，放置位置
        ApiKey apiKey = new ApiKey("token", "token", "header"); // 名字token，描述token, 位置header
        List<ApiKey> apiKeyList = new ArrayList<>();
        apiKeyList.add(apiKey);
        docket.securitySchemes(apiKeyList); // 添加到Docket上

        //令牌作用域 (设置成全局)
        // 创建认证对象
        AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");
        // 认证对象放到数组中
        AuthorizationScope[] scopes = {scope};
        // 将AuthorizationScope[] 再次封装到数组中
        // 创建对象
        SecurityReference reference = new SecurityReference("token", scopes);
        // 再次封装reference
        List refList = new ArrayList();
        refList.add(reference);
        // 再次封装成 SecurityContext
        SecurityContext context = SecurityContext.builder().securityReferences(refList).build();// 创建出context对象
        // 再次封装
        List cxtList = new ArrayList();
        cxtList.add(context);
        // 交给docket
        docket.securityContexts(cxtList);

        return docket;
    }

}
