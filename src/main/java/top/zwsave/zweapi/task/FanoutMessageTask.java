package top.zwsave.zweapi.task;

import com.rabbitmq.client.*;
import lombok.extern.java.Log;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;
import top.zwsave.zweapi.db.pojo.SystemMsgEntity;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
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

    public void send(SystemMsgEntity systemMsgEntity) {
        try (Channel channel = factory.newConnection().createChannel();) {
            channel.exchangeDeclare("system", BuiltinExchangeType.FANOUT); // BuiltinExchangeType.FANOUT == "fanout"
            HashMap hashMap = new HashMap();
            hashMap.put("systemUUID", systemMsgEntity.getUuid());
            hashMap.put("with", systemMsgEntity.getWith());
            AMQP.BasicProperties build = new AMQP.BasicProperties().builder().headers(hashMap).build();
            channel.basicPublish("system", "", build, systemMsgEntity.getMsg().getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void receive(String queueName) {
        System.out.println(queueName + "第几个");
        try (Channel channel = factory.newConnection().createChannel()){
            channel.exchangeDeclare("system", "fanout");
            channel.queueBind(queueName, "system", "");
            channel.basicQos(1);

            //channel.queueDeclare(queueName, false, false, false, null);

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    super.handleDelivery(consumerTag, envelope, properties, body);
                    String message = new String(body,"UTF-8");
                    log.info("received:" + message);
//                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            };

            channel.basicConsume(queueName,true,consumer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }



















    @Resource
    RabbitTemplate rabbitTemplate;

    public void newFanout(HashMap msg) {
        rabbitTemplate.convertAndSend("system", "", msg);
    }

    @Component
    @RabbitListener(queues = "systemMsg")
    class Consumers {
        @RabbitHandler()
        public void newReceive(HashMap msg) {
            System.out.println("是否监听到");
            String msg1 = (String) msg.get("msg");
            String msg2 = (String) msg.get("header");
            System.out.println(msg1);
            System.out.println(msg2);

        }
    }
}
