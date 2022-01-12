package top.zwsave.zweapi.exception;

import lombok.Data;

/**
 * @Author: Ja7
 * @Date: 2022-01-12 21:42
 */
@Data
public class ZweApiException extends RuntimeException{
    private String msg;
    private int code = 500;

    public ZweApiException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public ZweApiException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public ZweApiException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public ZweApiException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

}
