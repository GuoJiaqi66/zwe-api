package top.zwsave.zweapi.service.impl;

import org.springframework.stereotype.Service;
import top.zwsave.zweapi.config.shiro.JwtUtil;
import top.zwsave.zweapi.controller.form.MongoArticleCommentForm;
import top.zwsave.zweapi.db.dao.MongoArticleCommentDao;
import top.zwsave.zweapi.db.pojo.MongoArticleComment;
import top.zwsave.zweapi.service.MongoService;
import top.zwsave.zweapi.utils.Tool;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;

/**
 * @Author: Ja7
 * @Date: 2022-03-06 21:50
 */
@Service
public class MongoServiceImpl implements MongoService {

    @Resource
    Tool tool;

    @Resource
    JwtUtil jwtUtil;

    @Resource
    MongoArticleCommentDao mongoArticleCommentDao;

    @Override
    public String insertArticleComment(String token, MongoArticleCommentForm form) {
        MongoArticleComment mongoArticleComment = new MongoArticleComment();
        mongoArticleComment.setDel(0);
        mongoArticleComment.setUuid(tool.uuidString());
        mongoArticleComment.setTo(form.getTo());
        mongoArticleComment.setFrom(jwtUtil.getUserId(token) + "");
        mongoArticleComment.setContent(form.getContent());
        mongoArticleComment.setArticleId(form.getArticleId());
        mongoArticleComment.setCreateTime(new Date());
        String s = mongoArticleCommentDao.insertArticleComment(mongoArticleComment);
        return s;
    }
}
