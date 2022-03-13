package top.zwsave.zweapi.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.zwsave.zweapi.config.shiro.JwtUtil;
import top.zwsave.zweapi.db.dao.SystemMsgDao;
import top.zwsave.zweapi.db.pojo.SimpleMsgEntity;
import top.zwsave.zweapi.db.pojo.SystemMsg;
import top.zwsave.zweapi.db.pojo.SystemMsgRefEntity;
import top.zwsave.zweapi.service.COSService;
import top.zwsave.zweapi.service.SystemMsgService;
import top.zwsave.zweapi.task.SimpleMessageTask;
import top.zwsave.zweapi.utils.Tool;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: Ja7
 * @Date: 2022-03-13 12:42
 */
@Service
public class SystemMsgServiceImpl implements SystemMsgService {

    @Resource
    JwtUtil jwtUtil;

    @Resource
    SystemMsgDao systemMsgDao;

    @Resource
    COSService cosService;

    @Resource
    Tool tool;

    @Resource
    SimpleMessageTask simpleMessageTask;

    @Override
    public String newSystemVideoMsg(String token, MultipartFile file, String text) {
        String s = cosService.insertSystemVideoMsg(file);
        Long userId = jwtUtil.getUserId(token);
        Long uuid = tool.uuidLong();
        SystemMsg systemMsg = new SystemMsg();
        systemMsg.setContent(text);
        systemMsg.setCreatetime(new Date());
        systemMsg.setDelete("0");
        systemMsg.setId(uuid);
        systemMsg.setUserid(userId);
        systemMsg.setUrl(s);
        systemMsgDao.insertSelective(systemMsg);
        SimpleMsgEntity simpleMsgEntity = new SimpleMsgEntity();
        simpleMsgEntity.setUuid(uuid.toString());
        simpleMsgEntity.setSenderName("system");
        simpleMsgEntity.setSenderId(userId);
        simpleMsgEntity.setMsg(text);
        simpleMsgEntity.setSendTime(new Date());
        simpleMsgEntity.setUrl(s);
        simpleMessageTask.send("system", simpleMsgEntity);
        return "";
    }
}
