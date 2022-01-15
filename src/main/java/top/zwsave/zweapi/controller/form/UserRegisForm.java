package top.zwsave.zweapi.controller.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * user
 * @author 
 */
@Data
@ApiModel
public class UserRegisForm {
    private String id;

    @NotNull(message = "昵称不能为空")
    @ApiParam("昵称")
    private String nickname;

    @Pattern(regexp = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", message = "请输入正确的邮箱")
    private String mail;

    @NotNull(message = "登录名不能为空")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]{2,15}$", message = "登录名只能只能包含字母、数字和下划线, 3 ~ 15位")
    private String loginName;

    // @NotNull(message = "密码不能为空")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,32}$", message = "【密码】至少包含 数字和英文，长度6-32")
    private String password;

    private String imgPath;


    private static final long serialVersionUID = 1L;
}