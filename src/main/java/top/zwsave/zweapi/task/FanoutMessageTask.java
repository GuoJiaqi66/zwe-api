package top.zwsave.zweapi.task;

import com.rabbitmq.client.*;
import lombok.extern.java.Log;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import top.zwsave.zweapi.db.dao.SimpleMsgRefDao;
import top.zwsave.zweapi.db.pojo.SimpleMsgEntity;
import top.zwsave.zweapi.db.pojo.SimpleMsgRefEntity;
import top.zwsave.zweapi.db.pojo.SystemMsgEntity;
import top.zwsave.zweapi.exception.ZweApiException;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @Author: Ja7
 * @Date: 2022-03-11 15:25
 */
@Log
@Component
public class FanoutMessageTask {

    /**
     * 自动签收, 并且只能接收一次:
     *      -> 一个队列，只读一个
     * */

    @Resource
    ConnectionFactory factory;

    @Resource
    SimpleMsgRefDao simpleMsgRefDao;

    public void send(String eventName, SimpleMsgEntity msg) {
        try (Channel channel = factory.newConnection().createChannel()) {
            System.out.println(eventName);
            channel.exchangeDeclare(eventName, BuiltinExchangeType.FANOUT); // BuiltinExchangeType.FANOUT == "fanout"
            HashMap hashMap = new HashMap();
            hashMap.put("id", msg.getUuid());
            hashMap.put("senderName", msg.getSenderName());
            hashMap.put("senderId", msg.getSenderId());
            hashMap.put("sendTime", msg.getSendTime());
            hashMap.put("url", msg.getUrl());
            AMQP.BasicProperties build = new AMQP.BasicProperties().builder().headers(hashMap).build();
            channel.basicPublish(eventName, "", build, msg.getMsg().getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void receive(String eventName, String queueName, Long userId) {
        try (Channel channel = factory.newConnection().createChannel()){
            channel.exchangeDeclare(eventName, "fanout");
            channel.queueDeclare(queueName, false, false, false, null);
            channel.queueBind(queueName, eventName, "");//把Queue、Exchange绑定
            channel.basicQos(1);


            Consumer callBack = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag,
                                           Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body) throws IOException {
                    System.out.println("=========业务代码========");

                    //路由的key
                    String routingKey = envelope.getRoutingKey();
                    //获取交换机信息
                    String exchange = envelope.getExchange();
                    //获取消息ID
                    long deliveryTag = envelope.getDeliveryTag();

                    String id = properties.getHeaders().get("id").toString();

                    Boolean aBoolean = simpleMsgRefDao.selectHave(id);
                    if (!aBoolean) {
                        return;
                    }

                    SimpleMsgRefEntity simpleMsgRefEntity = new SimpleMsgRefEntity();
                    simpleMsgRefEntity.setLastFlag(true);
                    simpleMsgRefEntity.setMessageId(id);
                    simpleMsgRefEntity.setReadFlag(false);
                    simpleMsgRefEntity.setReceiverId(userId);

                    simpleMsgRefDao.insertSimpleMsgRefEntity(simpleMsgRefEntity);

                    //获取消息信息
                    String message = new String(body,"utf-8");
                    System.out.println(
                            "routingKey:" + routingKey +
                                    ",exchange:" + exchange +
                                    ",deliveryTag:" + deliveryTag +
                                    ",message:" + message);

                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            };
            AMQP.Queue.DeclareOk declareOk = channel.queueDeclarePassive(queueName);
            int messageCount = declareOk.getMessageCount();
            System.out.println("数量" + messageCount);
            while (messageCount > 0) {
                channel.basicConsume(queueName, false, callBack);
                messageCount--;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    @Async
    public void asyncReceive(String eventName, String queueName, Long userId){
        receive(eventName, queueName, userId);
    }
}
