package top.zwsave.zweapi.service;

import top.zwsave.zweapi.controller.form.UserLoginForm;
import top.zwsave.zweapi.controller.form.UserRegisForm;
import top.zwsave.zweapi.db.pojo.User;

/**
 * @Author: Ja7
 * @Date: 2022-01-13 16:29
 */
public interface UserService {

    public int userRegistered(UserRegisForm form);

    User login(UserLoginForm form);

    /*Integer selectTotalUser();

    User selectUserByLoginName(String loginName);*/
}
