package top.zwsave.zweapi.task;

import com.rabbitmq.client.*;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import top.zwsave.zweapi.db.dao.SimpleMsgDao;
import top.zwsave.zweapi.db.dao.SimpleMsgRefDao;
import top.zwsave.zweapi.db.pojo.SimpleMsgEntity;
import top.zwsave.zweapi.db.pojo.SimpleMsgRefEntity;
import top.zwsave.zweapi.exception.ZweApiException;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Ja7
 * @Date: 2022-03-11 11:43
 */
@Log
@Component
public class SimpleMessageTask {

    /**
     * 消息种类:
     *      系统消息:
     *          改名--改密码.....
     *              messageEntity
     *              messageRefEntity
     *
     *      点赞消息:
     *          谁点赞了你
     *          收藏
     *
     *      私信消息:
     * */

    @Resource
    ConnectionFactory faction;

    @Resource
    SimpleMsgDao simpleMsgDao;

    @Resource
    SimpleMsgRefDao simpleMsgRefDao;


    public void send(String topic, SimpleMsgEntity msg) {
        System.out.println(topic);
        try (Connection connection = faction.newConnection();
             Channel channel = connection.createChannel();
             ) {
            channel.queueDeclare(topic, true, false, false, null);
            HashMap hashMap = new HashMap();
            hashMap.put("id", msg.getUuid());
            hashMap.put("senderName", msg.getSenderName());
            hashMap.put("senderId", msg.getSenderId());
            hashMap.put("sendTime", msg.getSendTime());
            hashMap.put("url", msg.getUrl());
            AMQP.BasicProperties build = new AMQP.BasicProperties().builder().headers(hashMap).build();
            channel.basicPublish("", topic, build, msg.getMsg().getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ZweApiException("消息队列发布失败");
        }
    }

    @Async
    public void asyncSend(String topic, SimpleMsgEntity msg) {
        send(topic, msg);
    }

    public void receive(String topic, Boolean autoAck, Long userId) {
        int i = 0; // ack应答后才会执行下一个
        try (Connection connection = faction.newConnection();
             Channel channel = connection.createChannel()
        ) {
            channel.queueDeclare(topic, true, false, false, null);
            while (true) {
                GetResponse getResponse = channel.basicGet(topic, autoAck);
                if (getResponse != null) {
                    AMQP.BasicProperties props = getResponse.getProps();
                    Map<String, Object> headers = props.getHeaders();
                    String id = headers.get("id").toString();
//                    String senderName = headers.get("senderName").toString();
//                    String senderId = headers.get("senderId").toString();
//                    Object sendTime = headers.get("sendTime");
//                    String url = headers.get("url").toString();
//                    byte[] body = getResponse.getBody();
//                    String msg = new String(body, "UTF-8");
//                    System.out.println(msg);

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

                    if (autoAck) {
                        long deliveryTag = getResponse.getEnvelope().getDeliveryTag();
                        channel.basicAck(deliveryTag, false);
                    }
                    long deliveryTag = getResponse.getEnvelope().getDeliveryTag();
                    channel.basicAck(deliveryTag, false);

                    i++;
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ZweApiException("接收消息失败");
        }
    }

    @Async
    public void asyncReceive(String topic, Boolean autoAck, Long userId) {
        receive(topic, autoAck, userId);
    }

    public void deleteTopic(String topic) {
        try (Channel channel = faction.newConnection().createChannel()) {
            channel.queueDelete(topic);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ZweApiException("删除队列失败");
        }
    }

    @Async
    public void asyncDeleteTopic(String topic) {
        deleteTopic(topic);
    }
}
