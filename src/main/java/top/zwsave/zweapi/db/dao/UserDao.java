package top.zwsave.zweapi.db.dao;

import io.swagger.models.auth.In;
import top.zwsave.zweapi.db.pojo.User;

public interface UserDao {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    Integer selectTotalUser();

    User selectUserByLoginName(String loginName);
}