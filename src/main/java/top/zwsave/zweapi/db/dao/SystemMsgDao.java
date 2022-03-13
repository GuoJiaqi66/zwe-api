package top.zwsave.zweapi.db.dao;

import top.zwsave.zweapi.db.pojo.SystemMsg;

public interface SystemMsgDao {
    int deleteByPrimaryKey(Long id);

    int insert(SystemMsg record);

    int insertSelective(SystemMsg record);

    SystemMsg selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SystemMsg record);

    int updateByPrimaryKey(SystemMsg record);
}