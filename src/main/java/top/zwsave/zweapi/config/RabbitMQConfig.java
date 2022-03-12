package top.zwsave.zweapi.config;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Ja7
 * @Date: 2022-01-11 22:15
 */
@Configuration
public class RabbitMQConfig {

    @Bean
//    @Scope("prototype")
    public ConnectionFactory getFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setVirtualHost("/zwe-api");
        return factory;
    }

    // 系统消息队列
    @Bean
    public Queue systemMsg() {
        return new Queue("systemMsg");
    }
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("system");
    }
    @Bean
    Binding bindingExchange(Queue systemMsg, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(systemMsg).to(fanoutExchange);
    }
}
