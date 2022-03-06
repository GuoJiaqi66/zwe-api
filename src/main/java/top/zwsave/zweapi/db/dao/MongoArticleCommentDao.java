package top.zwsave.zweapi.db.dao;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
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

    public String deleteArticleComment(String commentId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("uuid").is(commentId));
        DeleteResult articleComment = mongoTemplate.remove(query, MongoArticleComment.class);
        long deletedCount = articleComment.getDeletedCount();
        // 判断deleteCount 是否 > 1, 否则就报错不存在
        return deletedCount + "";
    }

    public String delArticleComment(String commentId) {
        Query query = new Query(Criteria.where("uuid").is(commentId));
        Update update = new Update();
        update.set("del", 1);
        UpdateResult upsert = mongoTemplate.upsert(query, update, MongoArticleComment.class);
        long modifiedCount = upsert.getModifiedCount();
        // 判空
        return modifiedCount + "";
    }
}
