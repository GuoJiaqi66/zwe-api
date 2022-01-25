package top.zwsave.zweapi.service.impl;

import cn.hutool.core.util.RandomUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import top.zwsave.zweapi.config.shiro.JwtUtil;
import top.zwsave.zweapi.controller.form.UserLoginForm;
import top.zwsave.zweapi.controller.form.UserRegisForm;
import top.zwsave.zweapi.controller.form.UserRepairInfo;
import top.zwsave.zweapi.db.dao.UserDao;
import top.zwsave.zweapi.db.dao.UserFollowDao;
import top.zwsave.zweapi.db.pojo.User;
import top.zwsave.zweapi.db.pojo.UserFollow;
import top.zwsave.zweapi.exception.ZweApiException;
import top.zwsave.zweapi.service.UserService;
import top.zwsave.zweapi.utils.CopyUtil;
import top.zwsave.zweapi.utils.SnowFlake;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: Ja7
 * @Date: 2022-01-13 16:29
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserDao userDao;

    @Resource
    SnowFlake snowFlake;

    @Resource
    JwtUtil jwtUtil;

    @Resource
    UserFollowDao userFollowDao;

    @Override
    public int userRegistered(UserRegisForm form) {
        User user1 = userDao.selectUserByLoginName(form.getLoginName());
        if (user1 != null) {
            throw new ZweApiException("登录名已被注册");
        }
        User user = CopyUtil.copy(form, User.class);
        Integer count = userDao.selectTotalUser();
        String id = RandomUtil.randomNumbers(15).trim();
        long l = Long.parseLong(id);
        user.setId(l);
        user.setCreateTime(new Date());
        user.setFansCount(0);
        user.setRanking(count);
        user.setFollowCount(0);
        int insert = userDao.insert(user);
        return insert;
    }

    @Override
    public User login(UserLoginForm form) {
        User user = userDao.selectUserByLoginName(form.getLoginName());
        if (ObjectUtils.isEmpty(user)) {
            throw new ZweApiException("用户名不存在");
        } else {
            if (user.getLoginName().equals(form.getLoginName())) {
                if (user.getPassword().equals(form.getPassword())) {
                    return user;
                } else {
                    throw new ZweApiException("密码不正确");
                }
            } else {
                throw new ZweApiException("登录名不正确");
            }
        }

    }

    @Override
    public int userRepairInfo(UserRepairInfo info) {
        String b = userDao.selectUserById(info.getId());
        if (b != null) {
            if (info.getPassword() != null || info.getNickname() != null || info.getMail() != null) {
                int i = userDao.updateUserInfoById(info);
                return i;
            } else {
                throw new ZweApiException("没有可以修改的信息");
            }
        } else {
            throw new ZweApiException("用户id信息不正确");
        }
    }

    @Override
    public Integer follow(String token, Long id) {
        selectNoteByUseredId(id);
        selectUserIsPresence(id);
        Long userId = jwtUtil.getUserId(token);
        userDao.userFollowAdd(userId);
        userDao.userFansAdd(id);
        UserFollow userFollow = new UserFollow();
        userFollow.setCreateTime(new Date());
        userFollow.setDelete("0");
        String s = RandomUtil.randomNumbers(15);
        long l = Long.parseLong(s);
        userFollow.setId(l);
        userFollow.setUseredId(id);
        userFollow.setUserId(userId);
        int insert = userFollowDao.insert(userFollow);
        return insert;
    }

    @Override
    public List selectAllFans(String token) {
        Long userId = jwtUtil.getUserId(token);
        ArrayList<HashMap> hashMaps = userFollowDao.selectAllFans(userId);
        return hashMaps;
    }

    @Override
    public Integer removeFollow(String token, Long id) {
        Long userId = jwtUtil.getUserId(token);
        // TODO: 2022-01-25 我的关注 -1  
        userDao.userFollowRemove(userId);
        // TODO: 2022-01-25 被关注 -1
        userDao.userFansRemove(id);
        // TODO: 2022-01-25 user_follow -> delete = "1"
        Integer integer = userFollowDao.removeFollow(userId);
        return integer;
    }

    public Long selectNoteByUseredId(Long id) {
        Long aLong = userFollowDao.selectNoteByUseredId(id);
        if (aLong != null) {
            throw new ZweApiException("已关注此用户");
        }
        return aLong;
    }

    /**
     * 判断用户是否存在
     * */
    String selectUserIsPresence(Long id) {
        String s = userDao.selectUsetStatus(id);
        if (s == null || s == "1") {
            throw new ZweApiException("用户不存在");
        }
        return s;
    }
}
