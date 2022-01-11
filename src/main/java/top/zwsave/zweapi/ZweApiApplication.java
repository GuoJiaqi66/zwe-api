package top.zwsave.zweapi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@ComponentScan("top")
@MapperScan("top.zwsave.zweapi.db.dao")
@EnableAsync
public class ZweApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZweApiApplication.class, args);
    }

}
