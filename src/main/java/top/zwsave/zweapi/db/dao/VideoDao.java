package top.zwsave.zweapi.db.dao;

import top.zwsave.zweapi.db.pojo.Video;

public interface VideoDao {
    int deleteByPrimaryKey(Long id);

    int insert(Video record);

    int insertSelective(Video record);

    Video selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Video record);

    int updateByPrimaryKey(Video record);
}