package top.zwsave.zweapi.controller.form;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @Author: Ja7
 * @Date: 2022-01-15 12:28
 */
@Data
@ApiModel
public class UserLoginForm {


    private String loginName;

    private String password;
}
