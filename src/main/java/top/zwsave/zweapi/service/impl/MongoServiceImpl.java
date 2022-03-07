package top.zwsave.zweapi.service.impl;

import org.springframework.stereotype.Service;
import top.zwsave.zweapi.config.shiro.JwtUtil;
import top.zwsave.zweapi.controller.form.MongoInsertCommentForm;
import top.zwsave.zweapi.controller.form.MongoCommentPageForm;
import top.zwsave.zweapi.db.dao.MongoArticleCommentDao;
import top.zwsave.zweapi.db.dao.MongoVideoCommentDao;
import top.zwsave.zweapi.db.dao.UserDao;
import top.zwsave.zweapi.db.pojo.MongoArticleComment;
import top.zwsave.zweapi.db.pojo.MongoVideoComment;
import top.zwsave.zweapi.service.MongoService;
import top.zwsave.zweapi.utils.Tool;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

    @Resource
    MongoVideoCommentDao mongoVideoCommentDao;

    @Resource
    UserDao userDao;

    @Override
    public String insertArticleComment(String token, MongoInsertCommentForm form) {
        MongoArticleComment mongoArticleComment = new MongoArticleComment();
        mongoArticleComment.setDel(0);
        mongoArticleComment.setUuid(tool.uuidString());
        mongoArticleComment.setTo(form.getTo());
        mongoArticleComment.setFrom(jwtUtil.getUserId(token) + "");
        mongoArticleComment.setContent(form.getContent());
        mongoArticleComment.setArticleId(form.getRootId());
        mongoArticleComment.setCreateTime(new Date());
        String s = mongoArticleCommentDao.insertArticleComment(mongoArticleComment);
        return s;
    }

    @Override
    public String delArticleComment(String uuid) {
        String s = mongoArticleCommentDao.delArticleComment(uuid);
        return s;
    }

    @Override
    public ArrayList selectArticleByPage(MongoCommentPageForm form) {
        int star = form.getPageNum() * form.getPageSize();
        int end = form.getPageSize();
        List list = mongoArticleCommentDao.selectArticleByPage(form.getTo(), star, end);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            MongoArticleComment o =(MongoArticleComment) list.get(i);
            HashMap hashMap = userDao.selectUserByIdUseMongo(Long.parseLong(o.getFrom()));
            hashMap.put("uuid", o.getUuid());
            hashMap.put("articleId", o.getArticleId());
            hashMap.put("from", o.getFrom());
            hashMap.put("to", o.getTo());
            hashMap.put("content", o.getContent());
            hashMap.put("createTime", o.getCreateTime());
            arrayList.add(hashMap);
        }
        return arrayList;
    }


    /**video
     * */
    @Override
    public String insertVideoComment(String token, MongoInsertCommentForm form) {
        MongoVideoComment mongoVideoComment = new MongoVideoComment();
        mongoVideoComment.setDel(0);
        mongoVideoComment.setUuid(tool.uuidString());
        mongoVideoComment.setTo(form.getTo());
        mongoVideoComment.setFrom(jwtUtil.getUserId(token) + "");
        mongoVideoComment.setContent(form.getContent());
        mongoVideoComment.setVideoId(form.getRootId());
        mongoVideoComment.setCreateTime(new Date());
        String s = mongoVideoCommentDao.insertVideoComment(mongoVideoComment);
        return s;
    }

    @Override
    public String delVideoComment(String uuid) {
        String s = mongoVideoCommentDao.delVideoComment(uuid);
        return s;
    }

    @Override
    public ArrayList selectVideoByPage(MongoCommentPageForm form) {
        int star = form.getPageNum() * form.getPageSize();
        int end = form.getPageSize();
        List list = mongoVideoCommentDao.selectVideoByPage(form.getTo(), star, end);
        return (ArrayList) list;
    }
}
