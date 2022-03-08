package top.zwsave.zweapi.service;

import top.zwsave.zweapi.controller.form.MongoInsertCommentForm;
import top.zwsave.zweapi.controller.form.MongoCommentPageForm;
import top.zwsave.zweapi.db.pojo.MongoArticleComment;
import top.zwsave.zweapi.db.pojo.MongoVideoComment;

import java.util.ArrayList;

/**
 * @Author: Ja7
 * @Date: 2022-03-06 21:50
 */
public interface MongoService {

    String insertArticleComment(String token, MongoInsertCommentForm form);

    String delArticleComment(String uuid);

    ArrayList<MongoArticleComment> selectArticleByPage(MongoCommentPageForm form);


    /**
     * video
     * */
    String insertVideoComment(String token, MongoInsertCommentForm form);

    String delVideoComment(String uuid);

    ArrayList<MongoVideoComment> selectVideoByPage(MongoCommentPageForm form);

    /*ArrayList selectArticleById(String id);*/
}
