package top.zwsave.zweapi.controller.form;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @Author: Ja7
 * @Date: 2022-01-15 12:28
 */
@Data
@ApiModel
public class UserLoginForm {

    @NotNull(message = "登录名不能为空")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]{2,15}$", message = "登录名只能只能包含字母、数字和下划线, 3 ~ 15位")
    private String loginName;

    @Pattern(regexp = "^([A-Z]|[a-z]|[0-9]|[`~!@#$%^&*()+=|{}':;',\\\\\\\\[\\\\\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“'。，、？]){6,20}$", message = "【密码】由字母数字字符组成")
    private String password;
}
