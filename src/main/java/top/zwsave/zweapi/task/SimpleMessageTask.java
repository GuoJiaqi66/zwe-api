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
            hashMap.put("messageId", msg.getUuid());
            AMQP.BasicProperties build = new AMQP.BasicProperties().builder().headers(hashMap).build();
            channel.basicPublish("", topic, build, msg.getMsg().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ZweApiException("消息队列发布失败");
        }
    }

    @Async
    public void asyncSend(String topic, SimpleMsgEntity msg) {
        send(topic, msg);
    }

    public void receive(String topic) {
        int i = 0;
        try (Channel channel = faction.newConnection().createChannel()) {
            channel.queueDeclare(topic, true, false, false, null);
            while (true) {
                GetResponse getResponse = channel.basicGet(topic, false);
                if (getResponse != null) {
                    AMQP.BasicProperties props = getResponse.getProps();
                    Map<String, Object> headers = props.getHeaders();
                    String messageId = headers.get("messageId").toString();
                    byte[] body = getResponse.getBody();
                    String s = new String(body);

                    // 创建ref entity 并添加到数据库
                    SimpleMsgRefEntity simpleMsgRefEntity = new SimpleMsgRefEntity();
                    simpleMsgRefEntity.setMessageId(messageId);
                    simpleMsgRefEntity.setReadFlag(false);
                    simpleMsgRefEntity.setLastFlag(true);
                    simpleMsgRefEntity.setReceiverId(Integer.parseInt(topic));

                    log.info(simpleMsgRefEntity.toString());

                    simpleMsgRefDao.insertSimpleMsgRefEntity(simpleMsgRefEntity);

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
