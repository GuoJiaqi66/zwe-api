package top.zwsave.zweapi.service.impl;

import org.springframework.stereotype.Service;
import top.zwsave.zweapi.config.shiro.JwtUtil;
import top.zwsave.zweapi.db.dao.PersonalMessageDao;
import top.zwsave.zweapi.service.MessageService;
import top.zwsave.zweapi.task.SimpleMessageTask;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: Ja7
 * @Date: 2022-01-12 18:30
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    JwtUtil jwtUtil;

    @Resource
    PersonalMessageDao personalMessageDao;

    @Resource
    SimpleMessageTask simpleMessageTask;

    @Override
    public HashMap getPersonalMsg(String token) {
        Long userId = jwtUtil.getUserId(token);
        System.out.println("=================userId"+ userId + "=================");
//        simpleMessageTask.receive(userId+"");
        HashMap hashMap = personalMessageDao.selectAllNotReadPersonalMsg(userId);

        /**
         * 交给前端，拿到消息，然后请求头像，昵称
         * */
//        List articleStar = (List) hashMap.get("articleStar");
//        hashMap.get("articleLike");
//        hashMap.get("videoStar");
//        hashMap.get("videoLike");
        return hashMap;
    }

}
