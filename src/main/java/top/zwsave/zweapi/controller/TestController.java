package top.zwsave.zweapi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zwsave.zweapi.db.dao.MongoDBTestDao;
import top.zwsave.zweapi.db.dao.UserDao;
import top.zwsave.zweapi.db.pojo.MongoDBTest;
import top.zwsave.zweapi.db.pojo.User;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Ja7
 * @Date: 2022-01-10 22:18
 */
@RestController
@Api("test接口")
public class TestController {

    @Resource
    UserDao userDao;

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    MongoDBTestDao mongoDBTestDao;

    @ApiOperation("测试启动")
    @GetMapping("/test")
    public String test() {
        return "SUCCESS";
    }

    @ApiOperation("测试mysql")
    @GetMapping("/testDB")
    public int testDB() {
        String username = "TEST";
        String password = "TEST";
        String nickname = "Test";
        int fansCounts = 2;
        int followCounts = 59;
        int likeCounts = 100;
        User user = new User();
        user.setId("12121212");
        user.setUsername(username);
        user.setPassword(password);
        user.setNickname(nickname);
        user.setFansCounts(fansCounts);
        user.setFollowCounts(followCounts);
        user.setReceiveLikeCounts(likeCounts);
        int insert = userDao.insert(user);
        return insert;
    }

    @ApiOperation("测试Redis")
    @GetMapping("/testRedis")
    public Boolean testRedis() {
        /*
        * LOG.info("key不存在，放入：{}，过期 {} 秒", key, second);
        * redisTemplate.opsForValue().set(key, key, second, TimeUnit.SECONDS);
        * */
        String key = "test:TestRedis";
        redisTemplate.opsForValue().set(key, "SUCCESS", 30, TimeUnit.MINUTES);
        Boolean aBoolean = redisTemplate.hasKey(key);
        return aBoolean;
    }


    @ApiOperation("测试MongoDB")
    @GetMapping("/testMongoDB")
    public String testMongoDB() {
        MongoDBTest mongoDBTest = new MongoDBTest();
        mongoDBTest.setSenderId("121212");
        mongoDBTest.setSenderName("TEST");
        mongoDBTest.setSendTime(new Date());
        mongoDBTest.setUuid("12121212");
        String insert = mongoDBTestDao.insert(mongoDBTest);
        return insert;
    }
}
