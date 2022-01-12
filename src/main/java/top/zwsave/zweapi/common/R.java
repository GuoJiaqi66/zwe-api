package top.zwsave.zweapi.common;

import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class R extends HashMap<String, Object> {

    // 默认构造器
    public R() {
        // 请求成功
        // code 200
        put("code", HttpStatus.SC_OK);
        put("msg", "success");
    }

    // 链式调用put方法
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    // 静态工厂方法
    public static R ok() {
        return new R();
    }

    // 重载方法ok方法
    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    // 重载ok方法带参数
    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map); //将所哟map放进去
        return r;
    }

    // 定义error
    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    // 定义状态码为500的error
    public static R error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static R error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
    }

}
