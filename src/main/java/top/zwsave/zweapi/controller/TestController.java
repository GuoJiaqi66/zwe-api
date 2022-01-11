package top.zwsave.zweapi.controller;

import com.rabbitmq.client.Channel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zwsave.zweapi.db.dao.MongoDBTestDao;
import top.zwsave.zweapi.db.dao.UserDao;
import top.zwsave.zweapi.db.pojo.MongoDBTest;
import top.zwsave.zweapi.db.pojo.RabbitmqtestOrder;
import top.zwsave.zweapi.db.pojo.User;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Ja7
 * @Date: 2022-01-10 22:18
 */
@RestController
@Api("test接口")
public class TestController {

    @Resource
    UserDao userDao;

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    MongoDBTestDao mongoDBTestDao;

    @Resource
    RabbitTemplate rabbitTemplate;

    @ApiOperation("测试启动")
    @GetMapping("/test")
    public String test() {
        return "SUCCESS";
    }

    @ApiOperation("测试mysql")
    @GetMapping("/testDB")
    public int testDB() {
        String username = "TEST";
        String password = "TEST";
        String nickname = "Test";
        int fansCounts = 2;
        int followCounts = 59;
        int likeCounts = 100;
        User user = new User();
        user.setId("12121212");
        user.setUsername(username);
        user.setPassword(password);
        user.setNickname(nickname);
        user.setFansCounts(fansCounts);
        user.setFollowCounts(followCounts);
        user.setReceiveLikeCounts(likeCounts);
        int insert = userDao.insert(user);
        return insert;
    }

    @ApiOperation("测试Redis")
    @GetMapping("/testRedis")
    public Boolean testRedis() {
        /*
        * LOG.info("key不存在，放入：{}，过期 {} 秒", key, second);
        * redisTemplate.opsForValue().set(key, key, second, TimeUnit.SECONDS);
        * */
        String key = "test:TestRedis";
        redisTemplate.opsForValue().set(key, "SUCCESS", 30, TimeUnit.MINUTES);
        Boolean aBoolean = redisTemplate.hasKey(key);
        return aBoolean;
    }


    @ApiOperation("测试MongoDB")
    @GetMapping("/testMongoDB")
    public String testMongoDB() {
        MongoDBTest mongoDBTest = new MongoDBTest();
        mongoDBTest.setSenderId("121212");
        mongoDBTest.setSenderName("TEST");
        mongoDBTest.setSendTime(new Date());
        mongoDBTest.setUuid("12121212");
        String insert = mongoDBTestDao.insert(mongoDBTest);
        return insert;
    }

    @GetMapping("/testRabbitMQ")
    public void sendRabbitMQTestOrder() {
        RabbitmqtestOrder order = new RabbitmqtestOrder();
        order.setId("1212121212");
        order.setMessageId("1212121212");
        order.setName("test");

        // 消息唯一id
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(order.getMessageId());

        rabbitTemplate.convertAndSend(
                "test-exchange", // exchange
                "test.routingKey", // rotingKey
                order, // 消息体内容
                correlationData // correlationData 消息唯一id
        ); // 发送消息
    }


    /*
    * RabbitMQ签收消息 消费者
    * */
    // 替代了手动创建
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "test", durable = "true"), // 监听的队列 是否持久化
                    exchange = @Exchange(name = "test-exchange", durable = "true", type = "topic"),
                    key = "test.#" // routerKey

            )
    ) //
    @RabbitHandler // rabbitmq接收消息
    @GetMapping("/testRabbitMQOnMessage")
    public void receiptRabbitMQTest(@Payload RabbitmqtestOrder order,
                                    @Headers Map<String, Object> headers,
                                    Channel channel) { // 接收实体，消息Properties(Headers 要用map接收) channel通道
        // 消费者操作
        System.out.println("-------------收到消息,开始消费-----------");
        System.out.println("订单ID：" + order.getMessageId());

        Long deliveryTag =(Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        // 手工签收ACK
        try {
            channel.basicAck(deliveryTag, false); // 不支持批量签收
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
