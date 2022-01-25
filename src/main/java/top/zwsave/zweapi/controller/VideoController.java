package top.zwsave.zweapi.controller;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.zwsave.zweapi.common.R;
import top.zwsave.zweapi.controller.form.PageReq;
import top.zwsave.zweapi.service.VideoService;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: Ja7
 * @Date: 2022-01-22 14:43
 */
@RestController
@Api("视频接口")
public class VideoController {

    @Autowired
    COSClient cosClient;

    @Autowired
    VideoService videoService;


    @PostMapping("/newvideo")
    @ApiOperation("上传新作品")
    public R newVideo(@RequestHeader("token") String token, @RequestParam("file") MultipartFile file, @RequestParam("text") String text, @RequestParam("visible") String visible){
        String s = videoService.newVideo(token, file, text, visible);
        return R.ok("发布成功").put("url", s);
    }

    @PostMapping("/selectvideobypage")
    @ApiOperation("分页查询video")
    public R selectByPage(@Valid @RequestBody PageReq pageReq) {
        List list = videoService.selectByPage(pageReq.getPageNum(), pageReq.getPageSize());
        return R.ok("请求成功").put("res", list);
    }

    @ApiOperation("删除video")
    @GetMapping("/deletevideo/{id}")
    public R delete(@PathVariable Long id) {
        Integer deletearticle = videoService.deleteVideo(id);
        return R.ok("删除成功");
    }

    @ApiOperation("video私有/共有")
    @GetMapping("/selectVideoVisible/{id}")
    public R selectVisible(@PathVariable Long id) {
        String s = videoService.selectVisibleById(id);
        return R.ok("查询成功").put("res", s);
    }
}
