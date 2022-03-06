package top.zwsave.zweapi.db.dao;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import top.zwsave.zweapi.db.pojo.MongoArticleComment;

import javax.annotation.Resource;

/**
 * @Author: Ja7
 * @Date: 2022-03-06 19:35
 */
@Repository
public class MongoArticleCommentDao {

    @Resource
    private MongoTemplate mongoTemplate;

    public String insertArticleComment(MongoArticleComment mongoArticleComment) {
        MongoArticleComment insert = mongoTemplate.insert(mongoArticleComment);
        return insert.get_id();
    }
}
