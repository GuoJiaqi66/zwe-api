package top.zwsave.zweapi.db.dao;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import top.zwsave.zweapi.db.pojo.SystemMsgRefEntity;

import javax.annotation.Resource;

/**
 * @Author: Ja7
 * @Date: 2022-03-11 12:47
 */
@Repository
public class SystemMsgRefDao {
    @Resource
    private MongoTemplate mongoTemplate;

    public String insertSystemMsgRefEntity(SystemMsgRefEntity entity) {
        SystemMsgRefEntity save = mongoTemplate.save(entity);
        return save.get_id();
    }
}
