package top.zwsave.zweapi.service.impl;

import cn.hutool.core.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.zwsave.zweapi.config.shiro.JwtUtil;
import top.zwsave.zweapi.db.dao.VideoDao;
import top.zwsave.zweapi.db.pojo.Video;
import top.zwsave.zweapi.service.COSService;
import top.zwsave.zweapi.service.VideoService;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: Ja7
 * @Date: 2022-01-23 11:04
 */
@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    COSService cosService;

    @Resource
    VideoDao videoDao;

    @Override
    public String newVideo(String token, MultipartFile file, String text, String visible) {
        Long userId = jwtUtil.getUserId(token);
        String id = RandomUtil.randomNumbers(15).trim();
        long l = Long.parseLong(id);
        Video video = new Video();
        video.setCreateTime(new Date());
        video.setDelete("0");
        video.setId(l);
        video.setLikeCounts(0);
        video.setStar(0);
        video.setUserId(userId);
        video.setVideoPath("string");
        video.setStatus("0");
        video.setVisible(visible);
        video.setText(text);
        int insert = videoDao.insert(video);
        String s = cosService.insertVideo(file, token, l);
        return s;
    }
}
