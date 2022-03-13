package top.zwsave.zweapi.zweapi;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.zwsave.zweapi.db.dao.ArticleDao;
import top.zwsave.zweapi.db.dao.MongoArticleCommentDao;
import top.zwsave.zweapi.db.pojo.MongoArticleComment;
import top.zwsave.zweapi.db.pojo.SimpleMsgEntity;
import top.zwsave.zweapi.db.pojo.SystemMsgEntity;
import top.zwsave.zweapi.service.SystemMsgService;
import top.zwsave.zweapi.task.FanoutMessageTask;
import top.zwsave.zweapi.task.RoutMessageTask;
import top.zwsave.zweapi.task.SimpleMessageTask;
import top.zwsave.zweapi.task.TestFanout;
import top.zwsave.zweapi.utils.SnowFlake;
import top.zwsave.zweapi.utils.Tool;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

    @Resource
    ArticleDao articleDao;
    @Test
    void pageHelp() {
        /*PageHelper pageHelper = new PageHelper();
        pageHelper.startPage(2, 3);
        List<Article> article = articleDao.selectByPage();
        PageInfo<Article> pageInfo = new PageInfo(article);
        List list = pageInfo.getList();
        System.out.println("----------------");
        System.out.println(list);
        System.out.println("----------------");
        System.out.println(pageInfo.getTotal());
        System.out.println("----------------");
        System.out.println(pageInfo.getPageNum());
        System.out.println("----------------");
        System.out.println(pageInfo.getPageSize());*/

    }

    @Resource
    MongoArticleCommentDao mongoArticleCommentDao;

    @Test
    void mongo() {
        for (int i = 0; i < 30; i++) {
            MongoArticleComment mongoArticleComment = new MongoArticleComment();
            mongoArticleComment.setArticleId("1");
            mongoArticleComment.setContent("test");
            mongoArticleComment.setFrom("2");
            mongoArticleComment.setTo("3");
            mongoArticleComment.setUuid(i+"");
            mongoArticleComment.setDel(0);
            mongoArticleCommentDao.insertArticleComment(mongoArticleComment);
        }
        /*System.out.println(s);*/
    }

    @Test
    void mongoDel() {
        String s = mongoArticleCommentDao.deleteArticleComment("111");
        System.out.println(s);
    }

    @Test
    void mongoDelete() {
        String s = mongoArticleCommentDao.delArticleComment("111");
        System.out.println(s);
    }

    @Test
    void mongoSelect() {
        List select = mongoArticleCommentDao.selectArticleByPage("3", 30, 10);
        System.out.println("======= select.size()" + select.size() + "+++++");
        for (int i = 0; i < select.size(); i++) {
            Object o = select.get(i);
            System.out.println("====" + i + "===");
            System.out.println(o);
        }
    }

    @Resource
    SimpleMessageTask task;
    @Test
    void testRab() {
//        SimpleMsgEntity simpleMsgEntity = new SimpleMsgEntity();
//        simpleMsgEntity.setMsg("测试数据");
//        simpleMsgEntity.setSenderId(328);
//        simpleMsgEntity.setSenderName("g j q");
//        simpleMsgEntity.setSendTime(new Date());
//        simpleMsgEntity.setUuid(new Tool().uuidString());
//        task.send("1000020", simpleMsgEntity);
    }
    @Test
    void tesRabR() {
//        task.asyncReceive("0000");
//        task.receive("1000020");
    }


    @Resource
    FanoutMessageTask fanoutMessageTask;
    @Test
    void testFanout() {
        SystemMsgEntity systemMsgEntity = new SystemMsgEntity();
        systemMsgEntity.setUuid("111111111111111");
        systemMsgEntity.setMsg("test => fanout=====");
        systemMsgEntity.setSender("system");
        systemMsgEntity.setWith("附加");
        fanoutMessageTask.send(systemMsgEntity);
    } @Test
    void testFanou() {
        SystemMsgEntity systemMsgEntity = new SystemMsgEntity();
        systemMsgEntity.setUuid("111111111111111");
        systemMsgEntity.setMsg("fanout=====");
        systemMsgEntity.setSender("system");
        systemMsgEntity.setWith("附加");
        fanoutMessageTask.send(systemMsgEntity);
    }
    @Test
    void q1() {
        fanoutMessageTask.receive("121212121212121212");
    }@Test
    void q2() {
        fanoutMessageTask.receive("12");
    }@Test
    void q3() {
        fanoutMessageTask.receive("1515");
    }



    @Resource
    TestFanout testFanout;
    @Test
    void w1() {
        HashMap hashMap = new HashMap();
        hashMap.put("msg", "hello world");
        hashMap.put("header", "G J Q");
//        fanoutMessageTask.newFanout(hashMap);
        testFanout.newFanout(hashMap);
    }


    /**
     * 接口发送系统消息->
     * rabbitMQ -> 接到消息
     *
     * */



    @Resource
    RoutMessageTask routMessageTask;
    /*@Test
    void w3() {
        routMessageTask.routSend();
    }
    @Test
    void e1() {
        routMessageTask.routReceive();
    }*/

    @Resource
    SimpleMessageTask simpleMessageTask;
    @Test
    void e2() {
//        SimpleMsgEntity simpleMsgEntity = new SimpleMsgEntity();
//        simpleMsgEntity.setMsg("test => hello success");
//        simpleMsgEntity.setSenderId(202111);
//        simpleMsgEntity.setSenderName("g j q");
//        simpleMsgEntity.setUuid("ssssssssssss");
//        simpleMsgEntity.getSendTime();
//        simpleMessageTask.send("system", simpleMsgEntity);
    }
    @Test
    void e3() {
//        simpleMessageTask.receive("system", false);
    }



}
