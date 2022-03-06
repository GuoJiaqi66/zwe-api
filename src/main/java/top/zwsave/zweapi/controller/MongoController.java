package top.zwsave.zweapi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import top.zwsave.zweapi.common.R;
import top.zwsave.zweapi.controller.form.MongoArticleCommentForm;
import top.zwsave.zweapi.db.pojo.MongoArticleComment;
import top.zwsave.zweapi.service.MongoService;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @Author: Ja7
 * @Date: 2022-03-06 21:48
 */
@RestController
@Api("Mongo Controller")
public class MongoController {

    @Resource
    MongoService mongoService;

    @ApiOperation("article 增加comment")
    @PostMapping("/insertArticleComment")
    public R insertArticleComment(@RequestHeader("token") String token, @RequestBody MongoArticleCommentForm form) {
        String s = mongoService.insertArticleComment(token, form);
        return R.ok().put("res", s);
    }

    @ApiOperation("articleComment 删除")
    @GetMapping("/delArticleComment/{uuid}")
    public R delArticleComment(@RequestHeader("token") String token, @PathVariable String uuid) {
        String s = mongoService.delArticleComment(uuid);
        return R.ok().put("res", s);
    }
}
