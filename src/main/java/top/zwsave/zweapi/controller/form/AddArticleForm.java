package top.zwsave.zweapi.controller.form;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: Ja7
 * @Date: 2022-01-21 13:16
 */
@ApiModel
@Data
public class AddArticleForm {
    /**
    * 公开还是私有
    * */
    private Object visible;

    @NotNull(message = "文案不能为空")
    private String text;


}
