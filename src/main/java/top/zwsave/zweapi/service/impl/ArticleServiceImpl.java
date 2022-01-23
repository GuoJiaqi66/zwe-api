package top.zwsave.zweapi.service.impl;

import cn.hutool.core.util.RandomUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.zwsave.zweapi.config.shiro.JwtUtil;
import top.zwsave.zweapi.controller.form.AddArticleForm;
import top.zwsave.zweapi.db.dao.ArticleDao;
import top.zwsave.zweapi.db.pojo.Article;
import top.zwsave.zweapi.exception.ZweApiException;
import top.zwsave.zweapi.service.ArticleService;
import top.zwsave.zweapi.service.COSService;
import top.zwsave.zweapi.utils.CopyUtil;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: Ja7
 * @Date: 2022-01-21 11:59
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    ArticleDao articleDao;

    @Resource
    JwtUtil jwtUtil;

    @Resource
    COSService cosService;


    @Override
    public String insert(MultipartFile file, AddArticleForm article, String token) {
        if (article.getText() == null) {
            throw new ZweApiException("文案不能为空");
        }
        Long userId = jwtUtil.getUserId(token);
        String s = RandomUtil.randomNumbers(15).trim();
        long id = Long.parseLong(s);
        Object visible = article.getVisible();
        String s1 = visible + "";
        article.setVisible(s1);
        Article art = CopyUtil.copy(article, Article.class);
        art.setId(id);
        art.setCreateTime(new Date());
        art.setLikeCounts(0);
        art.setStatus("0");
        art.setDelete("0");
        art.setUserId(userId);
        art.setImgPath("string");
        art.setStar(0);
        int insert = articleDao.insert(art);

        String s2 = cosService.insertArticleImg(file, token, id);

        return s2;
    }
}
