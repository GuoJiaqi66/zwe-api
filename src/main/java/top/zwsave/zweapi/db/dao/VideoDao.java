package top.zwsave.zweapi.db.dao;

import top.zwsave.zweapi.db.pojo.Video;

import java.util.ArrayList;
import java.util.HashMap;

public interface VideoDao {
    int deleteByPrimaryKey(Long id);

    int insert(Video record);

    int insertSelective(Video record);

    Video selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Video record);

    int updateByPrimaryKey(Video record);

    ArrayList<HashMap> selectByPage();

    Integer deleteVideoById(Long id);

    String selectVisibleById(Long id);

    Integer likeCountAdd(Long id);
}