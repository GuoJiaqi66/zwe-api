package top.zwsave.zweapi.service;

import org.springframework.stereotype.Service;
import top.zwsave.zweapi.controller.form.MongoArticleCommentForm;
import top.zwsave.zweapi.db.pojo.MongoArticleComment;

import java.util.HashMap;

/**
 * @Author: Ja7
 * @Date: 2022-03-06 21:50
 */
public interface MongoService {
    String insertArticleComment(String token, MongoArticleCommentForm form);
}
