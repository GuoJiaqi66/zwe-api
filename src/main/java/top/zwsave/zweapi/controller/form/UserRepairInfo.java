package top.zwsave.zweapi.controller.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;

/**
 * @Author: Ja7
 * @Date: 2022-01-17 19:08
 */
@Data
@ApiModel
public class UserRepairInfo {
    @NotNull(message = "id不能为空")
    private Long id;

    @ApiParam("昵称")
    private String nickname;

    @Pattern(regexp = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$", message = "请输入正确的邮箱")
    private String mail;


    @Pattern(regexp = "^([A-Z]|[a-z]|[0-9]|[`~!@#$%^&*()+=|{}':;',\\\\\\\\[\\\\\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“'。，、？]){6,20}$", message = "【密码】由字母数字字符组成")
    private String password;

    private String imgPath;
}
