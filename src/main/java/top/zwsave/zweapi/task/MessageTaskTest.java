package top.zwsave.zweapi.task;

import com.rabbitmq.client.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import top.zwsave.zweapi.db.pojo.MongoDBTest;
import top.zwsave.zweapi.db.pojo.RabbitmqtestOrder;
import top.zwsave.zweapi.service.RabbitMQMessageServiceTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Ja7
 * @Date: 2022-01-11 22:18
 */
@Component
public class MessageTaskTest {

    @Resource
    private ConnectionFactory factory;

    @Resource
    private RabbitMQMessageServiceTest rabbitMQMessageServiceTest;

    public void send(String topic, MongoDBTest mongoDBTest) {
        String id = rabbitMQMessageServiceTest.insertMessage(mongoDBTest);
        try (Connection connection = factory.newConnection();
                Channel channel = connection.createChannel();
        ) {
            channel.queueDeclare(topic, true, false, false, null);
            HashMap map = new HashMap();
            map.put("messageId", id);
            AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().headers(map).build();
            channel.basicPublish("", topic, properties, mongoDBTest.get_id().getBytes());
            System.out.println("消息发送成功");
        } catch (Exception e) {
            System.out.println("执行异常");
            e.printStackTrace();
        }
    }


    @Resource
    RabbitTemplate rabbitTemplate;

    @Async
    public void send(MongoDBTest order) {
        // 消息唯一id
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(order.getUuid());

        rabbitTemplate.convertAndSend(
                "test-exchange", // exchange
                "test.routingKey", // rotingKey
                order, // 消息体内容
                correlationData // correlationData 消息唯一id
        ); // 发送消息
    }

    public int receive(String topic) {
        int i = 0;
        try (Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
        ) {
            channel.queueDeclare(topic, true, false, false, null);
            while (true) {
                GetResponse response = channel.basicGet(topic, false);
                if (response != null) {
                    AMQP.BasicProperties properties = response.getProps();
                    Map<String, Object> map = properties.getHeaders();
                    String messageId = map.get("messageId").toString();
                    byte[] body = response.getBody();// 消息正文
                    String message = new String(body);
                    System.out.println("接收到的消息" + message);

                    // 将接收到消息存储到MongoDB

                    // 返回ack应答
                    long deliveryTag = response.getEnvelope().getDeliveryTag();
                    channel.basicAck(deliveryTag, false);

                    i++;
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 返回接受了i条消息
        return i;
    }
}
