package top.zwsave.zweapi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.zwsave.zweapi.common.R;
import top.zwsave.zweapi.config.shiro.JwtUtil;
import top.zwsave.zweapi.controller.form.AddArticleForm;
import top.zwsave.zweapi.controller.form.PageReq;
import top.zwsave.zweapi.service.ArticleService;
import top.zwsave.zweapi.service.COSService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

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
    @PostMapping("/selectarticlebypage")
    public R select(@Valid @RequestBody PageReq pageReq) {
        List hashMaps = articleService.selectByPage(pageReq.getPageNum(), pageReq.getPageSize());
        return R.ok("请求成功").put("res", hashMaps);
    }

    @ApiOperation("删除博客")
    @GetMapping("/deletearticle/{id}")
    public R delete(@PathVariable Long id) {
        Integer deletearticle = articleService.deleteArticle(id);
        return R.ok("删除成功");
    }

    @ApiOperation("博客私有/共有")
    @GetMapping("/selectArticleVisible/{id}")
    public R selectVisible(@PathVariable Long id) {
        String s = articleService.selectVisibleById(id);
        return R.ok("查询成功").put("res", s);
    }

    /**
     * 喜欢博客
     * */
    @ApiOperation("喜欢博客")
    @GetMapping("/likearticle/{id}")
    public R likeArticle(@RequestHeader("token") String token, @PathVariable Long id) {
        articleService.likeArticle(token, id);
        return R.ok("喜欢成功");
    }

    /**
     * 取消喜欢
     * */
    @ApiOperation("取消喜欢article")
    @GetMapping("/removelikearticle/{id}")
    public R removeLikeArticle(@RequestHeader("token") String token, @PathVariable Long id) {
        articleService.removeLikeArticle(token, id);
        return R.ok("取消喜欢");
    }

    /**
     * star博客
     * */
    @ApiOperation("star博客")
    @GetMapping("/stararticle/{id}")
    public R starArticle(@RequestHeader("token") String token, @PathVariable Long id) {
        articleService.starArticle(token, id);
        return R.ok("star成功");
    }

    /**
     * 取消star
     * */
    @ApiOperation("取消star-article")
    @GetMapping("/removestararticle/{id}")
    public R removeStarArticle(@RequestHeader("token") String token, @PathVariable Long id) {
        articleService.removeStarArticle(token, id);
        return R.ok("取消star");
    }

    /**
     * 浏览记录
     * */
    @ApiOperation("article浏览记录记载(前端判断是否登录,不登陆,将无法记录)")
    @GetMapping("/artclelook/{id}")
    public R articleLook(@RequestHeader("token") String token, @PathVariable Long id) {
        articleService.articleLook(token, id);
        return R.ok("记录成功添加");
    }
}
