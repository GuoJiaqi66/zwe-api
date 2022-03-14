package top.zwsave.zweapi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.zwsave.zweapi.common.R;
import top.zwsave.zweapi.config.shiro.JwtUtil;
import top.zwsave.zweapi.service.MessageService;
import top.zwsave.zweapi.service.SystemMsgService;
import top.zwsave.zweapi.task.FanoutMessageTask;
import top.zwsave.zweapi.task.SimpleMessageTask;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @Author: Ja7
 * @Date: 2022-03-12 12:22
 */
@RestController
@Api("message-controller")
public class MessageController {

    @Resource
    SystemMsgService systemMsgService;

    @Autowired
    private  JwtUtil jwtUtil;

    @Resource
    FanoutMessageTask fanoutMessageTask;

    @Autowired
    MessageService messageService;

    @Resource
    SimpleMessageTask simpleMessageTask;

    @PostMapping("/newSystemVideoMsg")
    @ApiOperation("上传系统video消息")
    public R newSystemVideoMsg(@RequestHeader("token") String token, @RequestParam("file") MultipartFile file, @RequestParam("text") String text){
        String s = systemMsgService.newSystemVideoMsg(token, file, text);
        return R.ok("发布成功").put("url", s);
    }

    /**
     * 需要签收的消息群发消息
     * */
    @GetMapping("/getSystemMsg")
    @ApiOperation("获取所有未读取的系统消息(待确认签收的消息)")
    public R getAllSystemUnReadMsg(@RequestHeader("token") String token) {
        Long userId = jwtUtil.getUserId(token);
        fanoutMessageTask.receive("SYSTEM", userId.toString(), userId);
        return R.ok().put("T", "t");
    }


    @ApiOperation("获取所有个人消息")
    @GetMapping("/getPersonalMsg")
    public R getPersonalMsg(@RequestHeader String token) {
        Long userId = jwtUtil.getUserId(token);
        simpleMessageTask.receive(userId+"");
        HashMap personalMsg = messageService.getPersonalMsg(token);

        return R.ok().put("res", personalMsg);
    }
}
