package top.zwsave.zweapi.db.dao;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import top.zwsave.zweapi.db.pojo.SimpleMsgRefEntity;

import javax.annotation.Resource;

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
}
