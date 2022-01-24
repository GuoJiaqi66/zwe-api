package top.zwsave.zweapi.zweapi;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.zwsave.zweapi.utils.SnowFlake;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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

    @Test
    void data() {
        Date date = new Date();
        System.out.println(date);
        String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"));
        System.out.println(format);
    }

    @Test
    void length() {
        String s ="https://zwe-api-1258360747.cos.ap-beijing.myqcloud.com/899322144639270/article/2022-01-22-11-50-35/FormData-item.png";
        int length = s.length();
        byte[] bytes=s.getBytes();
        System.out.println(bytes.length);
        System.out.println(length);
    }

    @Test
    void random() {
        String id = RandomUtil.randomNumbers(15).trim();
        long l = Long.parseLong(id);
        System.out.println(l);
        long l1 = RandomUtil.randomLong(15);
        System.out.println(l1);
        String i = RandomUtil.randomNumbers(15);
        System.out.println(i);
        long l2 = Long.parseLong(i);
        System.out.println(l2);
    }
}
