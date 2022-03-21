package top.zwsave.zweapi.db.dao;

import top.zwsave.zweapi.db.pojo.Show;

import java.util.ArrayList;

public interface ShowDao {
    int deleteByPrimaryKey(Long id);

    int insert(Show record);

    int insertSelective(Show record);

    Show selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Show record);

    int updateByPrimaryKey(Show record);

    Integer selectShowUserCount(String useredId);

    Integer selectUseredId(String useredId);

    Integer deleteShowUser(String id);

    ArrayList selectAllShowUser(Long userId);
}