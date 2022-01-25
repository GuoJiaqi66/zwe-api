package top.zwsave.zweapi.controller.form;

import com.github.pagehelper.PageHelper;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author: Ja7
 * @Date: 2022-01-25 10:37
 */
@Data
@ApiModel
public class PageReq {

    @NotNull(message = "当前页不为空")
    @Min(value = 0, message = "当前页不能小于0")
    private int pageNum;

    @Max(value = 20, message = "每页页长不能超过20  ")
    @NotNull(message = "页长不能为空")
    private int pageSize;

}
