package top.zwsave.zweapi.service;

import top.zwsave.zweapi.controller.form.UserLoginForm;
import top.zwsave.zweapi.controller.form.UserRegisForm;
import top.zwsave.zweapi.controller.form.UserRepairInfo;
import top.zwsave.zweapi.db.pojo.User;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: Ja7
 * @Date: 2022-01-13 16:29
 */
public interface UserService {

    public int userRegistered(UserRegisForm form);

    User login(UserLoginForm form);

    int userRepairInfo(UserRepairInfo info);

    Integer follow(String token, Long id);

    List selectAllFans(String token);

    Integer removeFollow(String token, Long id);

    HashMap selectUserInfoByUserId(String id);

    List selectAllFollow(Long userId);

    /*Integer selectTotalUser();

    User selectUserByLoginName(String loginName);*/
}
