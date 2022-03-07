package top.zwsave.zweapi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import top.zwsave.zweapi.common.R;
import top.zwsave.zweapi.controller.form.MongoInsertCommentForm;
import top.zwsave.zweapi.controller.form.MongoCommentPageForm;
import top.zwsave.zweapi.db.pojo.MongoArticleComment;
import top.zwsave.zweapi.db.pojo.MongoVideoComment;
import top.zwsave.zweapi.service.MongoService;

import javax.annotation.Resource;
import java.util.ArrayList;

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
    public R insertArticleComment(@RequestHeader("token") String token, @RequestBody MongoInsertCommentForm form) {
        String s = mongoService.insertArticleComment(token, form);
        return R.ok().put("res", s);
    }

    @ApiOperation("articleComment 删除")
    @GetMapping("/delArticleComment/{uuid}")
    public R delArticleComment(@RequestHeader("token") String token, @PathVariable String uuid) {
        String s = mongoService.delArticleComment(uuid);
        return R.ok().put("res", s);
    }

    @ApiOperation("分页查询 articleComment")
    @PostMapping("/selectArticleByPage")
    public R selectArticleByPage(@RequestBody MongoCommentPageForm form) {
        ArrayList<MongoArticleComment> mongoArticleComments = mongoService.selectArticleByPage(form);
        return R.ok().put("res", mongoArticleComments);
    }


    /**
     * video comment
     * */
    @ApiOperation("video 增加comment")
    @PostMapping("/insertVideoComment")
    public R insertVideoComment(@RequestHeader("token") String token, @RequestBody MongoInsertCommentForm form) {
        String s = mongoService.insertVideoComment(token, form);
        return R.ok().put("res", s);
    }

    @ApiOperation("videoComment 删除")
    @GetMapping("/delVideoComment/{uuid}")
    public R delVideoComment(@RequestHeader("token") String token, @PathVariable String uuid) {
        String s = mongoService.delVideoComment(uuid);
        return R.ok().put("res", s);
    }

    @ApiOperation("分页查询 videoComment")
    @PostMapping("/selectVideoByPage")
    public R selectVideoByPage(@RequestBody MongoCommentPageForm form) {
        ArrayList<MongoVideoComment> mongoVideoComments = mongoService.selectVideoByPage(form);
        return R.ok().put("res", mongoVideoComments);
    }
}
