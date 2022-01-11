package top.zwsave.zweapi.service.impl;

import org.springframework.stereotype.Service;
import top.zwsave.zweapi.db.dao.MongoDBTestDao;
import top.zwsave.zweapi.db.dao.RabbitmqtestOrderDao;
import top.zwsave.zweapi.db.pojo.MongoDBTest;
import top.zwsave.zweapi.service.RabbitMQMessageServiceTest;

import javax.annotation.Resource;

/**
 * @Author: Ja7
 * @Date: 2022-01-11 22:10
 */
@Service
public class RabbitMQMessageServiceTestImpl implements RabbitMQMessageServiceTest {

    @Resource
    MongoDBTestDao mongoDBTestDao;

    @Override
    public String insertMessage(MongoDBTest mongoDBTest) {
        String insert = mongoDBTestDao.insert(mongoDBTest);
        return insert;
    }
}
