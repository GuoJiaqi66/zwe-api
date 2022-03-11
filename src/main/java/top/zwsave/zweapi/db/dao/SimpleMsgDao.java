package top.zwsave.zweapi.db.dao;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import top.zwsave.zweapi.db.pojo.SimpleMsgEntity;

import javax.annotation.Resource;

/**
 * @Author: Ja7
 * @Date: 2022-03-11 12:42
 */
@Repository
public class SimpleMsgDao {

    @Resource
    private MongoTemplate mongoTemplate;

    public String insertSimpleMsgEntity(SimpleMsgEntity entity) {
        SimpleMsgEntity save = mongoTemplate.save(entity);
        return save.get_id();
    }
}
