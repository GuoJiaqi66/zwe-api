package top.zwsave.zweapi.db.dao;

import io.swagger.models.auth.In;
import top.zwsave.zweapi.controller.form.UserRepairInfo;
import top.zwsave.zweapi.db.pojo.User;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Handler;

public interface UserDao {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    Integer selectTotalUser();

    User selectUserByLoginName(String loginName);

    int updateUserInfoById(UserRepairInfo info);

    String selectUserById(Long id);

    int updataFaceImg(HashMap map);

    Integer userFollowAdd(Long id);

    String selectUsetStatus(Long id);

    Integer userFansAdd(Long id);

    Integer userFansRemove(Long id);

    Integer userFollowRemove(Long id);

    HashMap selectUserByIdUseMongo(Long userId);

    HashMap selectUserInfoByUserId(String id);

    List selectAllFollow(Long userId);

    HashMap selectAOPUser(String id);
}