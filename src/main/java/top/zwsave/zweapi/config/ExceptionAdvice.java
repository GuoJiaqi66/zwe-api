package top.zwsave.zweapi.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.zwsave.zweapi.exception.ZweApiException;

/*
* 精简：返回给客户端异常内容
* */
@Slf4j // 引入日志模块
@RestControllerAdvice  // 捕获各种异常
public class ExceptionAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 状态码
    @ExceptionHandler(Exception.class) // 捕获各种异常 只要是Exception异常的子类都可以被捕获
    public String exceptionHandler(Exception e) {
        log.error("执行异常 ", e);
        if (e instanceof MethodArgumentNotValidException) { // 后端验证失败抛出异常： MethodArgumentNotValidException
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
            // 将异常信息返回给前台
            return exception.getBindingResult().getFieldError().getDefaultMessage();
        } else if (e instanceof ZweApiException) { //若果是 EmosException类型
            ZweApiException exception = (ZweApiException) e;
            return exception.getMsg();
        } else if (e instanceof UnauthorizedException) { // 未授权的类型异常
            return "你不具备相关权限";
        } else {
            return "后端执行异常";
        }
    }

}
