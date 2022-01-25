package top.zwsave.zweapi.db.dao;

import top.zwsave.zweapi.db.pojo.UserFans;

public interface UserFansDao {
    int deleteByPrimaryKey(Long id);

    int insert(UserFans record);

    int insertSelective(UserFans record);

    UserFans selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserFans record);

    int updateByPrimaryKey(UserFans record);
}