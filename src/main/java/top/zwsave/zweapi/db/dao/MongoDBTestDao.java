package top.zwsave.zweapi.db.dao;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import top.zwsave.zweapi.db.pojo.MongoDBTest;

import javax.annotation.Resource;

/**
 * @Author: Ja7
 * @Date: 2022-01-10 23:09
 */
@Repository
public class MongoDBTestDao {

    @Resource
    private MongoTemplate mongoTemplate;

    public String insert(MongoDBTest entity) {
        MongoDBTest save = mongoTemplate.save(entity);
        return save.get_id();
    }
}
