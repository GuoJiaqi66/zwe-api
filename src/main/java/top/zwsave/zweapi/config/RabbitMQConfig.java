package top.zwsave.zweapi.config;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Ja7
 * @Date: 2022-01-11 22:15
 */
@Configuration
public class RabbitMQConfig {

    @Bean
    public ConnectionFactory getFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        return factory;
    }
}
