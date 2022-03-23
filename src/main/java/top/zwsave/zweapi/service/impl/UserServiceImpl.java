package top.zwsave.zweapi.service.impl;

import cn.hutool.core.util.RandomUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import top.zwsave.zweapi.config.shiro.JwtUtil;
import top.zwsave.zweapi.controller.form.AddShowUserForm;
import top.zwsave.zweapi.controller.form.UserLoginForm;
import top.zwsave.zweapi.controller.form.UserRegisForm;
import top.zwsave.zweapi.controller.form.UserRepairInfo;
import top.zwsave.zweapi.db.dao.ShowDao;
import top.zwsave.zweapi.db.dao.UserDao;
import top.zwsave.zweapi.db.dao.UserFollowDao;
import top.zwsave.zweapi.db.pojo.Show;
import top.zwsave.zweapi.db.pojo.User;
import top.zwsave.zweapi.db.pojo.UserFollow;
import top.zwsave.zweapi.exception.ZweApiException;
import top.zwsave.zweapi.service.MailService;
import top.zwsave.zweapi.service.UserService;
import top.zwsave.zweapi.utils.CopyUtil;
import top.zwsave.zweapi.utils.SnowFlake;
import top.zwsave.zweapi.utils.Tool;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
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

    @Resource
    MailService mailService;

    @Resource
    ShowDao showDao;

    @Resource
    Tool tool;

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
        mailService.htmlRegistrationMail(form.getMail(), count);
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

    /**
     * 头乱了：
     * 关注api存在问题,
     * user_follow 更新条件不能位user_id/usered_id
     * */
    @Override
    public Integer follow(String token, Long id) {

        selectUserIsPresence(id);
        Long userId = jwtUtil.getUserId(token);
        UserFollow userFollow1 = selectNoteByUseredId(userId, id);
        if (userFollow1 == null) {
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
        if (userFollow1.getDelete().equals("1") ) {
            userDao.userFollowAdd(userId);
            userDao.userFansAdd(id);
            HashMap map = new HashMap();
            map.put("id", userFollow1.getId());
            map.put("createTime", new Date());
            System.out.println(userFollow1.getId());
            Integer integer = userFollowDao.updataFollow(map);
            return integer;
        }
        return 0;
    }

    @Override
    public List selectAllFans(String token) {
        Long userId = jwtUtil.getUserId(token);
        ArrayList hashMaps = userFollowDao.selectAllFans(userId);
        return hashMaps;
    }

    @Override
    public Integer removeFollow(String token, Long id) {
        Long userId = jwtUtil.getUserId(token);
        HashMap hashMap = new HashMap();
        hashMap.put("userId", userId);
        hashMap.put("useredId", id);
        UserFollow userFollow = userFollowDao.selectIsFollow(hashMap);
        if (userFollow.getDelete().equals("1")) {
            throw new ZweApiException("已取消关注");
        }
        userDao.userFollowRemove(userId);
        userDao.userFansRemove(id);
        Integer integer = userFollowDao.removeFollow(userId, id);
        return integer;
    }

    @Override
    public HashMap selectUserInfoByUserId(String id) {
        HashMap hashMap = userDao.selectUserInfoByUserId(id);
        return hashMap;
    }

    @Override
    public List selectAllFollow(Long userId) {
        List list = userDao.selectAllFollow(userId);
        if (list.size() == 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            HashMap hashMap = selectUserInfoByUserId((String) list.get(i));
            arrayList.add(hashMap);
        }
        return arrayList;
    }

    @Override
    public Integer addShowUser(Long userId, AddShowUserForm form) {
        Integer integer = showDao.selectShowUserCount(userId + "");
        Integer integer1 = showDao.selectUseredId(form.getUseredId());
        // 更加严谨 => 应当查询delete = "1" 应当将他设为 "0"
        if (integer1 == 1) { throw new ZweApiException("已经存在了"); }
        if (integer > 6) { throw new ZweApiException("每个用户最多可以设置6个展示用户"); }
        Show show = new Show();
        show.setCount(integer);
        show.setCreateTime(new Date());
        show.setDelete("0");
        show.setFlag(form.getFlag());
        show.setId(tool.uuidLong());
        show.setTag(form.getTag());
        show.setUseredId(Long.parseLong(form.getUseredId()));
        show.setUserId(userId);
        int i = showDao.insert(show);
        return i;
    }

    @Override
    public Integer upDataShowUser(Long userId, HashMap map) {
        Show show = new Show();
        show.setId(Long.parseLong((String) map.get("id")));
        show.setFlag((String) map.get("flag"));
        show.setTag((String) map.get("tag"));
        int i = showDao.updateByPrimaryKeySelective(show);
        return i;
    }

    @Override
    public Integer deleteShowUser(Long userId, String id) {
        Integer integer = showDao.deleteShowUser(id);
        return integer;
    }

    @Override
    public ArrayList selectAllShowUser(Long userId) {
        ArrayList arrayList = showDao.selectAllShowUser(userId);
        if (arrayList.size() == 0) {
            return arrayList;
        }
        for (int i = 0; i < arrayList.size(); i++) {
            HashMap hashMap = (HashMap) arrayList.get(i);
            HashMap useredId = userDao.selectUserInfoByUserId(hashMap.get("useredId").toString());
            hashMap.put("useredInfo", useredId);
        }
        return arrayList;
    }

    @Override
    public HashMap selectAOPUser(String id) {
        HashMap hashMap = userDao.selectAOPUser(id);
        return hashMap;
    }

    @Override
    public ArrayList selectShowUserByUserId(String id) {
        ArrayList arrayList = showDao.selectShowUserByUserId(id);
        if (arrayList.size() == 0) {
            return arrayList;
        }
        for (int i = 0; i < arrayList.size(); i++) {
            HashMap hashMap = (HashMap) arrayList.get(i);
            HashMap useredId = userDao.selectUserInfoByUserId(hashMap.get("useredId").toString());
            hashMap.put("useredInfo", useredId);
        }
        return arrayList;
    }

    public UserFollow selectNoteByUseredId(Long userId, Long id) {
        HashMap map = new HashMap();
        map.put("userId", userId);
        map.put("useredId", id);
        UserFollow userFollow = userFollowDao.selectNoteByUseredId(map);
        if (userFollow == null) {
            return null;
        }
        String delete =(String) userFollow.getDelete();
        if (delete.equals("0")) {
            throw new ZweApiException("已关注此用户");
        }
        return userFollow;
    }

    /**
     * 判断用户是否存在
     * */
    String selectUserIsPresence(Long id) {
        System.out.println("idididididididiidididididididididdididididid" + id);
        String s = userDao.selectUsetStatus(id);
        System.out.println("ssssssssssssssssssssssssssssssssss" + s);
        if (s == null || s == "1") {
            throw new ZweApiException("用户不存在");
        }
        return s;
    }
}
