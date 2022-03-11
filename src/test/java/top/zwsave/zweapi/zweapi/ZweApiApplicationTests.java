package top.zwsave.zweapi.zweapi;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import top.zwsave.zweapi.db.dao.ArticleDao;
import top.zwsave.zweapi.db.dao.MongoArticleCommentDao;
import top.zwsave.zweapi.db.dao.SystemMsgDao;
import top.zwsave.zweapi.db.pojo.MongoArticleComment;
import top.zwsave.zweapi.db.pojo.SystemMsgEntity;
import top.zwsave.zweapi.task.RabbitMessageTask;
import top.zwsave.zweapi.utils.SnowFlake;
import top.zwsave.zweapi.utils.Tool;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
    RabbitMessageTask task;
    @Test
    void testRab() {
        SystemMsgEntity systemMsgEntity = new SystemMsgEntity();
        systemMsgEntity.setMsg("测试数据");
        systemMsgEntity.setSenderId(328);
        systemMsgEntity.setSenderName("g j q");
        systemMsgEntity.setSendTime(new Date());
        systemMsgEntity.setUuid(new Tool().uuidString());
        task.send("0003", systemMsgEntity);
    }
    @Test
    void tesRabR() {
//        task.asyncReceive("0000");
        task.receive("0003");
    }
}
