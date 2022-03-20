package top.zwsave.zweapi.controller.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @Author: Ja7
 * @Date: 2022-03-20 18:49
 */
@Data
@ApiModel
public class AddShowUserForm {

    @ApiParam("目标userId")
    @NotNull(message = "目标 userId 不能为空")
    private String useredId;

    @ApiParam("flag")
    private String flag;

    @ApiParam("tag")
    private String tag;

}
