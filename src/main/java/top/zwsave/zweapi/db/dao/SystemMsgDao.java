package top.zwsave.zweapi.db.dao;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import top.zwsave.zweapi.db.pojo.SystemMsgEntity;

import javax.annotation.Resource;

/**
 * @Author: Ja7
 * @Date: 2022-03-11 12:42
 */
@Repository
public class SystemMsgDao {

    @Resource
    private MongoTemplate mongoTemplate;

    public String insertSystemMsgEntity(SystemMsgEntity entity) {
        SystemMsgEntity save = mongoTemplate.save(entity);
        return save.get_id();
    }
}
