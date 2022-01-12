package top.zwsave.zweapi.task;

import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import top.zwsave.zweapi.service.MessageService;

import javax.annotation.Resource;

/**
 * @Author: Ja7
 * @Date: 2022-01-12 18:17
 */
@Component
@Slf4j
public class MessageTask {

    @Resource
    private ConnectionFactory factory;

    @Resource
    private MessageService messageService;



}
