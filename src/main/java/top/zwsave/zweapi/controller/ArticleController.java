package top.zwsave.zweapi.controller;

import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.multipart.MultipartFile;
import top.zwsave.zweapi.common.R;
import top.zwsave.zweapi.config.shiro.JwtUtil;
import top.zwsave.zweapi.controller.form.AddArticleForm;
import top.zwsave.zweapi.controller.form.PageReq;
import top.zwsave.zweapi.db.pojo.Article;
import top.zwsave.zweapi.service.ArticleService;
import top.zwsave.zweapi.service.COSService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.DoubleToIntFunction;

/**
 * @Author: Ja7
 * @Date: 2022-01-21 12:02
 */
@RestController
@Api("博客接口")
public class ArticleController {

    @Resource
    ArticleService articleService;

    @Resource
    JwtUtil jwtUtil;

    @Resource
    COSService cosService;

    @ApiOperation("新增博客")
    @PostMapping("/newarticle")
    public R insert(@RequestParam("file") MultipartFile file,@RequestParam("text") String text, @RequestParam("visible") String visible, @RequestHeader("token") String token) {
        AddArticleForm article = new AddArticleForm();
        article.setVisible(visible);
        article.setText(text);
        String insert = articleService.insert(file,article, token);
        return R.ok("新增成功").put("url", insert);
    }

    @ApiOperation("分页请求博客")
    @PostMapping("/selectbypage")
    public R select(@RequestBody PageReq pageReq) {
        List hashMaps = articleService.selectByPage(pageReq.getPageNum(), pageReq.getPageSize());
        return R.ok("请求成功").put("article", hashMaps);
    }
}
