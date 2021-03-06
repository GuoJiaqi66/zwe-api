package top.zwsave.zweapi.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.zwsave.zweapi.common.R;
import top.zwsave.zweapi.config.shiro.JwtUtil;
import top.zwsave.zweapi.controller.form.AddShowUserForm;
import top.zwsave.zweapi.controller.form.UserLoginForm;
import top.zwsave.zweapi.controller.form.UserRegisForm;
import top.zwsave.zweapi.controller.form.UserRepairInfo;
import top.zwsave.zweapi.db.pojo.User;
import top.zwsave.zweapi.service.COSService;
import top.zwsave.zweapi.service.UserService;
import top.zwsave.zweapi.task.FanoutMessageTask;
import top.zwsave.zweapi.task.SimpleMessageTask;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Ja7
 * @Date: 2022-01-13 16:40
 */
@RestController
@Api("用户注册登录接口")
public class UserController {

    @Resource
    UserService userService;

    @Resource
    RedisTemplate redisTemplate;

    @Resource
    JwtUtil jwtUtil;

    @Resource
    COSService cosService;

    @Value("${zwe-api.cache-expire}")
    int cacheExpire;

    @Resource
    SimpleMessageTask simpleMessageTask;

    @Resource
    FanoutMessageTask fanoutMessageTask;

    /**
    * 将token存储到redis
    * */
    public void saveTokenToRedis(String token, Long id) {
        redisTemplate.opsForValue().set(token, id, cacheExpire, TimeUnit.DAYS);
    }

    @PostMapping("/regist")
    @ApiOperation("用户注册")
    public R regist(@Valid @RequestBody UserRegisForm form) {
        String password = DigestUtils.md5DigestAsHex(form.getPassword().getBytes());
        form.setPassword(password);
        userService.userRegistered(form);
        return R.ok("用户注册成功");
    }

    @PostMapping("/login")
    @ApiOperation("用户登录")
    public R login(@RequestBody UserLoginForm form) {
        String password = DigestUtils.md5DigestAsHex(form.getPassword().getBytes());
        form.setPassword(password);
        User login = userService.login(form);
        Long id = login.getId();
//        fanoutMessageTask.receive("SYSTEM", id.toString(), id);
        String token = jwtUtil.createToken(id);
        saveTokenToRedis(token, id);
//        simpleMessageTask.asyncReceive("system", false, id);
        return R.ok("登陆成功").put("userInfo", login).put("token", token);
    }

    @PostMapping("/repairinfo")
    @ApiOperation("修改用户信息")
    public R userRepairInfo(@Valid @RequestBody UserRepairInfo info) {
        String s = null;
        if (info.getPassword() != null) {
            s = DigestUtils.md5DigestAsHex(info.getPassword().getBytes());
            info.setPassword(s);
        }
        System.out.println(info.getId());
        int i = userService.userRepairInfo(info);
        return R.ok("修改成功").put("修改个数", i);
    }


    @PostMapping("/uploadfaceimg")
    @ApiOperation("上传用户头像")
    public R uploadUserFaceImg(@RequestParam("file") MultipartFile file, @RequestHeader("token") String token) {
        Long userId = jwtUtil.getUserId(token);
        String s = cosService.uploadFaceImg(userId, file);
        return R.ok("头像上传成功").put("url", s);
    }

    @GetMapping("/follow/{id}")
    @ApiOperation("关注用户")
    public R userFollow(@RequestHeader("token") String token, @PathVariable Long id) {
        userService.follow(token, id);
        return R.ok("关注成功");
    }

    /**
     * 查询所有粉丝
     * */
    @GetMapping("/fanscounts")
    @ApiOperation("查询所有粉丝")
    public R fansCounts(@RequestHeader("token") String token) {
        List list = userService.selectAllFans(token);
        return R.ok("查询成功").put("res", list);
    }


    /**
     * 取消关注
     * 存在问题，当用户关注了之后，有取消关注，将delete设置为 "1", 当用户再次关注时，应当判断是否关注过，然后将delete设置为"0"，将create_time刷新
     * */
    @GetMapping("/removefollow/{id}")
    @ApiOperation("取消关注")
    public R removeFollow(@RequestHeader("token") String token, @PathVariable Long id) {
        Integer integer = userService.removeFollow(token, id);
        return R.ok("取消关注成功");
    }

    @GetMapping("/selectUserInfo/{id}")
    @ApiOperation("根据userId查询相关信息，用于首页消息通知")
    public R selectUserInfoByUserId(@RequestHeader("token") String token, @PathVariable String id) {
        Long userId = jwtUtil.getUserId(token);
        HashMap hashMap = userService.selectUserInfoByUserId(id);
        return R.ok().put("res", hashMap);
    }

    /**
     * 查询所有关注
     * */
    @GetMapping("/selectAllFollow")
    @ApiOperation("查询所有关注")
    public R selectAllFollow(@RequestHeader("token") String token) {
        Long userId = jwtUtil.getUserId(token);
        List list = userService.selectAllFollow(userId);
        return R.ok().put("res", list);
    }


    /**
     * 展示show:
     *      每当添加一个用户 count字段  +1
     *      若count字段 =规定的最大, 则停止添加
     *      */

    /**
     * 添加 show
     * */
    @PostMapping("/addShowUser")
    @ApiOperation("添加show用户")
    public R addShowUser(@RequestHeader("token") String token, @Valid @RequestBody AddShowUserForm form) {
        Long userId = jwtUtil.getUserId(token);
        Integer integer = userService.addShowUser(userId, form);

        return R.ok().put("res", "添加showUser成功");
    }

    /**
     * 更新/删除 接口
     * HashMap:
     *      id:
     *      flag
     *      tag:
     * */
    @PostMapping("/upDataShowUser")
    @ApiOperation("更新 showUser 信息")
    public R upDataShowUser(@RequestHeader("token") String token, @RequestBody HashMap map) {
        Long userId = jwtUtil.getUserId(token);
        Integer integer = userService.upDataShowUser(userId, map);

        return R.ok().put("res", "修改成功");
    }

    @GetMapping("/deleteShowUser/{id}")
    @ApiOperation("删除showUser")
    public R deleteShowUser(@RequestHeader("token") String token, @PathVariable String id) {
        Long userId = jwtUtil.getUserId(token);
        Integer integer = userService.deleteShowUser(userId, id);
        return R.ok().put("res", "删除成功");
    }

    /**
     * 查询所有showUser
     * */
    @GetMapping("/selectAllShowUser")
    @ApiOperation("查询所有showUser")
    public R selectAllShowUser(@RequestHeader("token") String token) {
        Long userId = jwtUtil.getUserId(token);
        ArrayList arrayList = userService.selectAllShowUser(userId);

        return R.ok().put("res", arrayList);
    }

    /**
     * 根据userId 查询除了password/登录名  以外的全部信息/如果(会)AOP可以排除password字段
     * */
    @GetMapping("/selectAOPUser/{id}")
    @ApiOperation("查询除了用户password/登录名之外的信息(AOP可解决)")
    public R selectAOPUser(@PathVariable String id) {
        HashMap hashMap = userService.selectAOPUser(id);
        return R.ok().put("res", hashMap);
    }

    // 根据userId查询所有showUser
    @GetMapping("/selectShowUserByUserId/{id}")
    @ApiOperation("根据userId查询查询所有showUser")
    public R selectShowUserByUserId(@PathVariable String id) {
        ArrayList arrayList = userService.selectShowUserByUserId(id);
        return R.ok().put("res", arrayList);
    }

}
