package top.zwsave.zweapi.zweapi;

import cn.hutool.core.lang.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.zwsave.zweapi.utils.SnowFlake;

import javax.annotation.Resource;

@SpringBootTest
class ZweApiApplicationTests {
    @Resource
    SnowFlake snowFlake;

    @Test
    void contextLoads() {
    }

    @Test
    void uuid() {
        UUID uuid = UUID.randomUUID();
        System.out.println(uuid);
        long l =snowFlake.nextId();
        System.out.println(l);
    }
}
