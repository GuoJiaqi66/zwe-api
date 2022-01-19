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
import top.zwsave.zweapi.controller.form.UserLoginForm;
import top.zwsave.zweapi.controller.form.UserRegisForm;
import top.zwsave.zweapi.controller.form.UserRepairInfo;
import top.zwsave.zweapi.db.pojo.User;
import top.zwsave.zweapi.service.COSService;
import top.zwsave.zweapi.service.UserService;

import javax.annotation.Resource;
import javax.validation.Valid;
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
        String token = jwtUtil.createToken(id);
        saveTokenToRedis(token, id);
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

}