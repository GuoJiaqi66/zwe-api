package top.zwsave.zweapi.db.dao;

import top.zwsave.zweapi.db.pojo.VideoStarUser;

import java.util.HashMap;

public interface VideoStarUserDao {
    int deleteByPrimaryKey(Long id);

    int insert(VideoStarUser record);

    int insertSelective(VideoStarUser record);

    VideoStarUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VideoStarUser record);

    int updateByPrimaryKey(VideoStarUser record);

    Integer updateByUserIdAndVideoId(HashMap<String, Object> stringObjectHashMap);

    VideoStarUser selectFromVideoStar(HashMap<String, Long> stringLongHashMap);
}