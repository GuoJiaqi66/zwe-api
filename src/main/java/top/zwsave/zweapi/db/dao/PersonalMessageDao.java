package top.zwsave.zweapi.db.dao;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import top.zwsave.zweapi.db.pojo.PersonalMessage;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: Ja7
 * @Date: 2022-03-13 20:09
 */
@Repository
public class PersonalMessageDao {

    @Resource
    private MongoTemplate mongoTemplate;

    public String insertPersonalMsg(PersonalMessage personalMessage) {
        PersonalMessage insert = mongoTemplate.insert(personalMessage);
        return insert.get_id();
    }

    public HashMap selectAllNotReadPersonalMsg(Long userId) {
        System.out.println("============= selectAllNotReadPersonalMsg" + userId + "==============");
        HashMap hashMap = new HashMap();
        List list = selectInit(userId, "article", "star");
        /*if (list.size() == 0) {
            list = Collections.singletonList("");
        }*/
        hashMap.put("articleStar", list);
        List list1 = selectInit(userId, "article", "like");
        /*if (list1.size() == 0) {
            list1 = Collections.singletonList("");
        }*/
        hashMap.put("articleLike", list1);
        List list2 = selectInit(userId, "video", "star");
        /*if (list2.size() == 0) {
            list2 = Collections.singletonList("");
        }*/
        hashMap.put("videoStar", list2);
        List list3 = selectInit(userId, "video", "like");
        /*if (list3.size() == 0) {
            list3 = Collections.singletonList("");
        }*/
        hashMap.put("videoLike", list3);

        System.out.println("==========="+ hashMap + "============");
        // TODO: 2022-03-13 消息的回复,私信
        return hashMap;
    }

    public List selectInit(Long userId, String type, String clazz) {
        Query query = new Query();
        query.addCriteria(Criteria.where("targetUserId").is(userId+"").and("readFlag").is(false)
                .and("type").is(type).and("clazz").is(clazz));
        List<PersonalMessage> articleStar = mongoTemplate.find(query, PersonalMessage.class);
        System.out.println( "==========res========="+articleStar);
        return articleStar;
    }
}
