package top.zwsave.zweapi.service;

import top.zwsave.zweapi.db.pojo.MongoDBTest;

/**
 * @Author: Ja7
 * @Date: 2022-01-11 22:07
 */
public interface RabbitMQMessageServiceTest {

    String insertMessage(MongoDBTest mongoDBTest);

}
