package top.zwsave.zweapi.task;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @Author: Ja7
 * @Date: 2022-03-13 10:53
 */
/*
@Component
public class TestFanout {
    @Resource
    RabbitTemplate rabbitTemplate;

    public void newFanout(HashMap msg) {
        rabbitTemplate.convertAndSend("system", "", msg);
    }

    @RabbitListener(queues = "systemMsg")
//    @RabbitHandler()
    public void newReceive(HashMap msg) {
        System.out.println("是否监听到");
        String msg1 = (String) msg.get("msg");
        String msg2 = (String) msg.get("header");
        System.out.println(msg1);
        System.out.println(msg2);
    }
}
*/
