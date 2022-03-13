package top.zwsave.zweapi.task;

import com.rabbitmq.client.*;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.attribute.AclEntry;
import java.util.concurrent.TimeoutException;

/**
 * @Author: Ja7
 * @Date: 2022-03-13 11:40
 */
@Component
public class RoutMessageTask {

    @Resource
    ConnectionFactory factory;

    public void routSend(String eventName, String meg) {
        try (Channel channel = factory.newConnection().createChannel()){
            channel.exchangeDeclare("USER", BuiltinExchangeType.DIRECT);
            channel.basicPublish("USER", eventName, null, meg.getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void routReceive(String userId, String eventName) {
        try (Channel channel = factory.newConnection().createChannel()) {
            channel.queueDeclare("userId", false, false, false, null);
            channel.queueBind(userId, "USER", eventName);
            channel.basicQos(1);
            DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String s = new String(body, "UTF-8");
                    System.out.println("消费者1："+s);
//                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            };
            channel.basicConsume("userId",true, defaultConsumer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    /*@RabbitListener(queues = "userId")
    @RabbitHandler
    public void listener(String content) {
        System.out.println(content);
    }*/
}
