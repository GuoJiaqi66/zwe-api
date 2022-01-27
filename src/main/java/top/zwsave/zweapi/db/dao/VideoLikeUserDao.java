package top.zwsave.zweapi.db.dao;

import top.zwsave.zweapi.db.pojo.VideoLikeUser;

import java.util.HashMap;

public interface VideoLikeUserDao {
    int deleteByPrimaryKey(Long id);

    int insert(VideoLikeUser record);

    int insertSelective(VideoLikeUser record);

    VideoLikeUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VideoLikeUser record);

    int updateByPrimaryKey(VideoLikeUser record);

    VideoLikeUser selectFromVideoLike(HashMap map);

    Integer updateByUserIdAndVideoId(HashMap map);
}