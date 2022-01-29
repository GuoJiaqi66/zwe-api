package top.zwsave.zweapi.db.dao;

import top.zwsave.zweapi.db.pojo.VideoLookUser;

public interface VideoLookUserDao {
    int deleteByPrimaryKey(Long id);

    int insert(VideoLookUser record);

    int insertSelective(VideoLookUser record);

    VideoLookUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(VideoLookUser record);

    int updateByPrimaryKey(VideoLookUser record);
}