package top.zwsave.zweapi.db.dao;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import top.zwsave.zweapi.db.pojo.SimpleMsgRefEntity;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Ja7
 * @Date: 2022-03-11 12:47
 */
@Repository
public class SimpleMsgRefDao {
    @Resource
    private MongoTemplate mongoTemplate;

    public String insertSimpleMsgRefEntity(SimpleMsgRefEntity entity) {
        SimpleMsgRefEntity save = mongoTemplate.save(entity);
        return save.get_id();
    }

    public Boolean selectHave(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("messageId").is(id));
        List<SimpleMsgRefEntity> simpleMsgRefEntities = mongoTemplate.find(query, SimpleMsgRefEntity.class);
        System.out.println(simpleMsgRefEntities);
        boolean empty = CollectionUtils.isEmpty(simpleMsgRefEntities);
        System.out.println(empty);
        return empty;
    }
}
