package top.zwsave.zweapi.config.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.zwsave.zweapi.common.CommonResp;
import top.zwsave.zweapi.common.R;
import top.zwsave.zweapi.config.shiro.ThreadLocalToken;

@Aspect
@Component
public class TokenAspect {
    @Autowired
    private ThreadLocalToken threadLocalToken;

    @Pointcut("execution(public * top.zwsave.zweapi.controller.*.*(..))")
    public void aspect(){

    }

    @Around("aspect()")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        /*CommonResp commonResp =(CommonResp) point.proceed();
        String token = threadLocalToken.getToken();
        if (token != null) {
        }
        return commonResp;*/

        R r=(R)point.proceed();
        String token=threadLocalToken.getToken();
        if(token!=null){
            r.put("token",token);
            threadLocalToken.clear();
        }
        return r;
    }
}
