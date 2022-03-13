package top.zwsave.zweapi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.zwsave.zweapi.common.R;
import top.zwsave.zweapi.service.SystemMsgService;

import javax.annotation.Resource;

/**
 * @Author: Ja7
 * @Date: 2022-03-12 12:22
 */
@RestController
@Api("message-controller")
public class MessageController {

    @Resource
    SystemMsgService systemMsgService;

    @PostMapping("/newSystemVideoMsg")
    @ApiOperation("上传系统video消息")
    public R newSystemVideoMsg(@RequestHeader("token") String token, @RequestParam("file") MultipartFile file, @RequestParam("text") String text){
        String s = systemMsgService.newSystemVideoMsg(token, file, text);
        return R.ok("发布成功").put("url", s);
    }

    /*@GetMapping("/getSystemMsg/{id}")
    @ApiOperation("获取所有未读取的系统消息")
    public R getAllSystemUnReadMsg(@PathVariable Long id) {

    }*/
}
