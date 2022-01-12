package top.zwsave.zweapi.common;

import lombok.Data;

/**
 * @Author: Ja7
 * @Date: 2022-01-12 22:00
 */
@Data
public class CommonResp<T> {
    private boolean success = true;

    private String message;

    private T content;


}
