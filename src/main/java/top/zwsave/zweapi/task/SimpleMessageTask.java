package top.zwsave.zweapi.task;

import com.rabbitmq.client.*;
import lombok.extern.java.Log;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import top.zwsave.zweapi.db.dao.PersonalMessageDao;
import top.zwsave.zweapi.db.dao.SimpleMsgDao;
import top.zwsave.zweapi.db.dao.SimpleMsgRefDao;
import top.zwsave.zweapi.db.pojo.PersonalMessage;
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
     *
     *
     *      sender: 谁
     *      type: 点赞/star/私信/回复
     *      target: articleId/commentId/videoId
     *      content:
     * */

    @Resource
    ConnectionFactory faction;

    @Resource
    PersonalMessageDao personalMessageDao;


    public void send(String topic, HashMap map) {
        System.out.println(topic);
        try (Connection connection = faction.newConnection();
             Channel channel = connection.createChannel();
             ) {
            channel.queueDeclare(topic, true, false, false, null);
            HashMap hashMap = new HashMap();
            hashMap.put("uuid", map.get("uuid"));
            hashMap.put("sender", map.get("sender"));
            hashMap.put("targetId", map.get("targetId"));
            hashMap.put("type", map.get("type"));
            hashMap.put("clazz", map.get("clazz"));
            hashMap.put("targetUserId", map.get("targetUserId"));
            AMQP.BasicProperties build = new AMQP.BasicProperties().builder().headers(hashMap).build();
            if (hashMap.get("content") == null) {
                channel.basicPublish("", topic, build, null);
            } else {
                channel.basicPublish("", topic, build, hashMap.get("content").toString().getBytes("UTF-8"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ZweApiException("消息队列发布失败");
        }
    }

    @Async
    public void asyncSend(String topic, HashMap msg) {
        send(topic, msg);
    }

    public void receive(String topic) {
        int i = 0; // ack应答后才会执行下一个
        try (Connection connection = faction.newConnection();
             Channel channel = connection.createChannel()
        ) {
            channel.queueDeclare(topic, true, false, false, null);
            while (true) {
                GetResponse getResponse = channel.basicGet(topic, false);
                if (getResponse != null) {
                    AMQP.BasicProperties props = getResponse.getProps();
                    Map<String, Object> headers = props.getHeaders();
                    String uuid = headers.get("uuid").toString();
                    String sender = headers.get("sender").toString();
                    String targetId = headers.get("targetId").toString();
                    String type = headers.get("type").toString();
                    String clazz = headers.get("clazz").toString();
                    String targetUserId = headers.get("targetUserId").toString();

                    System.out.println("+++++++++++++++++++++"+ uuid + sender + "++++++++++++++++++");

                    byte[] body = getResponse.getBody();
                    String msg = new String(body, "UTF-8");

                    PersonalMessage personalMessage = new PersonalMessage();
                    personalMessage.setContent(msg);
                    personalMessage.setSender(sender);
                    personalMessage.setTargetId(targetId);
                    personalMessage.setType(type);
                    personalMessage.setUuid(uuid);
                    personalMessage.setReadFlag(false);
                    personalMessage.setClazz(clazz);
                    personalMessage.setTargetUserId(targetUserId);

                    long deliveryTag = getResponse.getEnvelope().getDeliveryTag();
                    channel.basicAck(deliveryTag, false);

                    personalMessageDao.insertPersonalMsg(personalMessage);

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
    public void asyncReceive(String topic) {
        receive(topic);
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
