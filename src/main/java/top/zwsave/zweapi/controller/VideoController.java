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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

    @ApiOperation("分页请求我的video")
    @PostMapping("/selectmyvideobypage")
    public R selectMyArticle(@Valid @RequestBody PageReq pageReq, @RequestHeader("token") String token) {
        List list = videoService.selectMyVideo(token, pageReq);
        return R.ok("请求成功").put("res", list);
    }

    @ApiOperation("删除video")
    @GetMapping("/deletevideo/{id}")
    public R delete(@RequestHeader("token") String token,@PathVariable Long id) {
        Integer deletearticle = videoService.deleteVideo(token, id);
        return R.ok("删除成功");
    }

    @ApiOperation("video私有/共有")
    @GetMapping("/selectVideoVisible/{id}")
    public R selectVisible(@PathVariable Long id) {
        String s = videoService.selectVisibleById(id);
        return R.ok("查询成功").put("res", s);
    }

    /**
     * 喜欢video
     * */
    @ApiOperation("喜欢video")
    @GetMapping("/likevideo/{id}")
    public R likeArticle(@RequestHeader("token") String token, @PathVariable Long id) {
        videoService.likeVideo(token, id);
        return R.ok("喜欢成功");
    }

    /**
     * 取消喜欢
     * */
    @ApiOperation("取消喜欢video")
    @GetMapping("/removelikevideo/{id}")
    public R removeLikeArticle(@RequestHeader("token") String token, @PathVariable Long id) {
        videoService.removeLikeVideo(token, id);
        return R.ok("取消喜欢");
    }


    /**
     * star-video
     * */
    @ApiOperation("star-video")
    @GetMapping("/starvideo/{id}")
    public R starArticle(@RequestHeader("token") String token, @PathVariable Long id) {
        videoService.starVideo(token, id);
        return R.ok("star成功");
    }

    /**
     * 取消star
     * */
    @ApiOperation("取消star-video")
    @GetMapping("/removestarvideo/{id}")
    public R removeStarArticle(@RequestHeader("token") String token, @PathVariable Long id) {
        videoService.removeStarVideo(token, id);
        return R.ok("取消star");
    }


    /**
     * 浏览记录
     * */
    @ApiOperation("video浏览记录记载(前端判断是否登录,不登陆,将无法记录)")
    @GetMapping("/videolook/{id}")
    public R videoLook(@RequestHeader("token") String token, @PathVariable Long id) {
        videoService.videoLook(token, id);
        return R.ok("记录成功添加");
    }

    /**
     * 查询浏览者记录
     * */
    @ApiOperation("video查询所有浏览者")
    @GetMapping("/videolookers/{id}")
    public R videoLookers(@PathVariable Long id, @RequestHeader("token") String token) {
        ArrayList<HashMap> hashMaps = videoService.selectAllVideoLooker(token, id);
        return R.ok().put("res", hashMaps);
    }

    /**
     * 查询所有喜欢article
     * */
    @ApiOperation("查询以喜欢的video列表")
    @PostMapping("/videolike")
    public R videoLike(@RequestHeader("token") String token, PageReq pageReq) {
        List list = videoService.selectAllLike(token, pageReq);
        return R.ok("查询成功").put("res", list);
    }

    /**
     * 查询所有-star-article
     * */
    @ApiOperation("查询以喜欢的video-star列表")
    @PostMapping("/videostar")
    public R videoStar(@RequestHeader("token") String token, PageReq pageReq) {
        List list = videoService.selectAllStar(token, pageReq);
        return R.ok("查询成功").put("res", list);
    }

    /**
     * 所有点赞者,star者
     * */
    @ApiOperation("根据videoId所有点赞者")
    @PostMapping("/selectvideoliker")
    public R selectVideoLiker(@RequestHeader("token")String token, Long id, PageReq pageReq) {
        List list = videoService.selectVideoLiker(token, id, pageReq);
        return R.ok("查询成功").put("res", list);
    }
    @ApiOperation("根据videoId所有点赞者")
    @PostMapping("/selectvideostarer")
    public R selectVideoStarer(@RequestHeader("token")String token, Long id, PageReq pageReq) {
        List list = videoService.selectVideoStarer(token, id, pageReq);
        return R.ok("查询成功").put("res", list);
    }
}
