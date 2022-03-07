package top.zwsave.zweapi.controller.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author: Ja7
 * @Date: 2022-03-07 13:35
 */
@Data
@ApiModel
public class MongoCommentPageForm {

    @NotNull(message = "who 不能为空")
    @ApiParam("分页查询 谁")
    private String to;

    @Min(value = 0, message = "最小不能超过0")
    @ApiParam("第几页")
    private int pageNum;

    @Max(value = 10, message = "步长不能超过10")
    @ApiParam("步长")
    private int pageSize;
}
