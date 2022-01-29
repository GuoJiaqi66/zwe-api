package top.zwsave.zweapi.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.zwsave.zweapi.config.shiro.JwtUtil;
import top.zwsave.zweapi.db.dao.VideoDao;
import top.zwsave.zweapi.db.dao.VideoLikeUserDao;
import top.zwsave.zweapi.db.dao.VideoLookUserDao;
import top.zwsave.zweapi.db.dao.VideoStarUserDao;
import top.zwsave.zweapi.db.pojo.*;
import top.zwsave.zweapi.exception.ZweApiException;
import top.zwsave.zweapi.service.COSService;
import top.zwsave.zweapi.service.VideoService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

    @Resource
    VideoLikeUserDao videoLikeUserDao;

    @Resource
    VideoStarUserDao videoStarUserDao;

    @Resource
    VideoLookUserDao videoLookUserDao;

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
        video.setLookCounts(0);
        int insert = videoDao.insert(video);
        String s = cosService.insertVideo(file, token, l);
        return s;
    }

    @Override
    public List selectByPage(int pageNum, int pageSize) {
        PageHelper pageHelper = new PageHelper();
        pageHelper.startPage(pageNum, pageSize);
        ArrayList<HashMap> hashMaps = videoDao.selectByPage();
        PageInfo<HashMap> hashMapPageInfo = new PageInfo<>(hashMaps);
        return hashMapPageInfo.getList();
    }

    @Override
    public Integer deleteVideo(Long id) {
        selectVisibleById(id);
        Integer integer = videoDao.deleteVideoById(id);
        return integer;
    }

    @Override
    public String selectVisibleById(Long id) {
        String s = videoDao.selectVisibleById(id);
        if (s == null) {
            throw new ZweApiException("video不存在");
        }
        return null;
    }

    @Override
    public Integer likeVideo(String token, Long id) {
        Long userId = jwtUtil.getUserId(token);
        VideoLikeUser articleLikeUser = selectFromVideoLike(userId, id);
        if (articleLikeUser == null) {
            VideoLikeUser articleLikeUser1 = new VideoLikeUser();
            articleLikeUser1.setVideoId(id);
            articleLikeUser1.setCreateTime(new Date());
            articleLikeUser1.setUserId(userId);
            String s = RandomUtil.randomNumbers(15).trim();
            long l = Long.parseLong(s);
            articleLikeUser1.setId(l);
            articleLikeUser1.setDelete("0");
            videoLikeUserDao.insert(articleLikeUser1);
        } else if (articleLikeUser.getDelete().equals("0")) {
            throw new ZweApiException("已喜欢");
        } else if (articleLikeUser.getDelete().equals("1")) {
            HashMap<String, Object> stringObjectHashMap = new HashMap<>();
            stringObjectHashMap.put("createTime", new Date());
            stringObjectHashMap.put("userId", userId);
            stringObjectHashMap.put("videoId", id);
            videoLikeUserDao.updateByUserIdAndVideoId(stringObjectHashMap);
        }

        Integer integer = videoDao.likeCountAdd(id);
        return integer;
    }

    VideoLikeUser selectFromVideoLike(Long userId, Long id) {
        HashMap<String, Long> stringLongHashMap = new HashMap<>();
        stringLongHashMap.put("userId", userId);
        stringLongHashMap.put("id", id);
        VideoLikeUser articleLikeUser = videoLikeUserDao.selectFromVideoLike(stringLongHashMap);
        if (articleLikeUser == null) {
            return null;
        }

        return articleLikeUser;
    }


    @Override
    public Integer removeLikeVideo(String token, Long id) {
        Long userId = jwtUtil.getUserId(token);
        VideoLikeUser articleLikeUser = selectFromVideoLike(userId, id);
        if (articleLikeUser.getDelete().equals("1")) {
            throw new ZweApiException("已取消夏欢");
        }
        Long id1 = articleLikeUser.getId();
        VideoLikeUser articleLikeUser1 = new VideoLikeUser();
        articleLikeUser1.setCreateTime(new Date());
        articleLikeUser1.setId(id1);
        articleLikeUser1.setDelete("1");
        videoLikeUserDao.updateByPrimaryKeySelective(articleLikeUser1);
        Integer integer = videoDao.likeCountRemove(id);
        return integer;
    }


    @Override
    public Integer starVideo(String token, Long id) {
        Long userId = jwtUtil.getUserId(token);
        VideoStarUser articleLikeUser = selectFromArticleStar(userId, id);
        if (articleLikeUser == null) {
            VideoStarUser articleLikeUser1 = new VideoStarUser();
            articleLikeUser1.setVideoId(id);
            articleLikeUser1.setCreateTime(new Date());
            articleLikeUser1.setUserId(userId);
            String s = RandomUtil.randomNumbers(15).trim();
            long l = Long.parseLong(s);
            articleLikeUser1.setId(l);
            articleLikeUser1.setDelete("0");
            videoStarUserDao.insert(articleLikeUser1);
        } else if (articleLikeUser.getDelete().equals("0")) {
            throw new ZweApiException("已喜欢");
        } else if (articleLikeUser.getDelete().equals("1")) {
            HashMap<String, Object> stringObjectHashMap = new HashMap<>();
            stringObjectHashMap.put("createTime", new Date());
            stringObjectHashMap.put("userId", userId);
            stringObjectHashMap.put("articleId", id);
            videoStarUserDao.updateByUserIdAndVideoId(stringObjectHashMap);
        }

        Integer integer = videoDao.starCountAdd(id);
        return integer;
    }

    VideoStarUser selectFromArticleStar(Long userId, Long id) {
        HashMap<String, Long> stringLongHashMap = new HashMap<>();
        stringLongHashMap.put("userId", userId);
        stringLongHashMap.put("id", id);
        VideoStarUser videoStarUser = videoStarUserDao.selectFromVideoStar(stringLongHashMap);
        if (videoStarUser == null) {
            return null;
        }
        return videoStarUser;
    }


    @Override
    public Integer removeStarVideo(String token, Long id) {
        Long userId = jwtUtil.getUserId(token);
        VideoStarUser articleLikeUser = selectFromArticleStar(userId, id);
        if (articleLikeUser.getDelete().equals("1")) {
            throw new ZweApiException("已取消夏欢");
        }
        Long id1 = articleLikeUser.getId();
        VideoStarUser articleLikeUser1 = new VideoStarUser();
        articleLikeUser1.setCreateTime(new Date());
        articleLikeUser1.setId(id1);
        articleLikeUser1.setDelete("1");
        videoStarUserDao.updateByPrimaryKeySelective(articleLikeUser1);
        Integer integer = videoDao.starCountRemove(id);
        return integer;
    }

    @Override
    public Integer videoLook(String token, Long id) {
        Long userId = jwtUtil.getUserId(token);
        VideoLookUser articleLookUser = new VideoLookUser();
        articleLookUser.setArticleId(id);
        articleLookUser.setCreateTime(new Date());
        articleLookUser.setId(Long.parseLong(RandomUtil.randomNumbers(15).trim()));
        articleLookUser.setUserId(userId);
        int insert = videoLookUserDao.insert(articleLookUser);
        videoDao.videoLookCountAdd(id);
        return insert;
    }

    @Override
    public ArrayList<HashMap> selectAllVideoLooker(String token, Long id) {
        ArrayList<HashMap> hashMaps = videoDao.selectVideoAllLooker(id);
        return hashMaps;
    }
}
