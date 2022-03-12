package top.zwsave.zweapi.controller;

import com.rabbitmq.client.Channel;
import io.swagger.annotations.Api;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @Author: Ja7
 * @Date: 2022-03-12 12:22
 */
@RestController
@Api("message-controller")
public class MessageController {

    @Resource
    RabbitTemplate rabbitTemplate;

    public void send(String msg) {
        rabbitTemplate.convertAndSend("system", "", msg);
    }


    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(value = "sys"), //创建临时队列
                    exchange = @Exchange(value = "system",type = "fanout")   //绑定的交换机
            )
    })
    public void receive(Message message, Channel channel) {
        System.out.println("fanoutReceiver01:"+message.toString() );
        System.out.println("fanoutReceiver01:"+message.getMessageProperties().getAppId() );
        System.out.println("fanoutReceiver01:"+message.getMessageProperties().getMessageId() );
        System.out.println("fanoutReceiver01:"+message.getMessageProperties().getReceivedExchange() );
        System.out.println("fanoutReceiver01:"+message.getMessageProperties().getReceivedRoutingKey() );
        System.out.println("fanoutReceiver01:"+message.getMessageProperties().getDeliveryTag() );
        System.out.println("fanoutReceiver01:"+message.getMessageProperties().getHeaders() );
        //应答消息队列
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(message);
    }
}
