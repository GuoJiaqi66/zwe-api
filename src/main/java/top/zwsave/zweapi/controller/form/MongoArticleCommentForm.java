package top.zwsave.zweapi.controller.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * @Author: Ja7
 * @Date: 2022-03-06 22:11
 */
@Data
@ApiModel
public class MongoArticleCommentForm {
    @ApiParam("to")
    private String to;

    @ApiParam("content")
    private String content;

    @ApiParam("articleID")
    private String articleId;
}
