package top.zwsave.zweapi.service.impl;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.zwsave.zweapi.config.shiro.JwtUtil;
import top.zwsave.zweapi.config.tencentCOS.Client;
import top.zwsave.zweapi.config.tencentCOS.Properties;
import top.zwsave.zweapi.db.dao.ArticleDao;
import top.zwsave.zweapi.db.dao.UserDao;
import top.zwsave.zweapi.db.pojo.Article;
import top.zwsave.zweapi.service.COSService;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import static com.qcloud.cos.demo.BucketRefererDemo.cosClient;

/**
 * @Author: Ja7
 * @Date: 2022-01-18 17:29
 */
@Service
public class COSServiceImpl implements COSService {
    @Resource
    Properties properties;

    @Resource
    COSClient cosClient;

    @Resource
    UserDao userDao;

    @Value("${tencent.baseUrl}")
    String baseUrl;

    @Resource
    JwtUtil jwtUtil;

    @Resource
    ArticleDao articleDao;


    @Override
    public String uploadFaceImg(Long userId, MultipartFile file) {
        String originalFilename = file.getOriginalFilename();

        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
        File localFile = null;
        Object res = null;
        try {
            localFile = File.createTempFile(String.valueOf(System.currentTimeMillis()),substring);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            file.transferTo(localFile); // 上传到目录下
        } catch (IOException e) {
            e.printStackTrace();
        }



        String key = userId + "/" + "face/" + originalFilename;
        PutObjectRequest putObjectRequest = new PutObjectRequest(properties.getBucket(), key, localFile);

        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

        System.out.println(putObjectResult);

        String ress = baseUrl + "/" + key;

        HashMap map = new HashMap();
        map.put("id", userId);
        map.put("imgPath", ress);
        int i = userDao.updataFaceImg(map);

        return ress;
    }

    @Override
    public String insertArticleImg(MultipartFile file, String token, Long id) {
        Long userId = jwtUtil.getUserId(token);


        String originalFilename = file.getOriginalFilename();

        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
        File localFile = null;
        Object res = null;
        try {
            localFile = File.createTempFile(String.valueOf(System.currentTimeMillis()),substring);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            file.transferTo(localFile); // 上传到目录下
        } catch (IOException e) {
            e.printStackTrace();
        }

        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"));

        String key = userId + "/" + "article/" + time + "/" + originalFilename;
        PutObjectRequest putObjectRequest = new PutObjectRequest(properties.getBucket(), key, localFile);

        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

        System.out.println(putObjectResult);

        String ress = baseUrl + "/" + key;

        Article article = new Article();
        article.setImgPath(ress);
        article.setId(id);
        articleDao.updateByPrimaryKeySelective(article);

        return ress;
    }
}
