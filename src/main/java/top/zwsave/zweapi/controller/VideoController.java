package top.zwsave.zweapi.controller;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.zwsave.zweapi.common.R;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: Ja7
 * @Date: 2022-01-22 14:43
 */
@RestController
@Api("视频接口")
public class VideoController {

    @Autowired
    COSClient cosClient;


    @PostMapping("/newvideo")
    @ApiOperation("上传新作品")
    public R newVideo(@RequestHeader("token") String token, @RequestParam("file") MultipartFile file){

        return R.ok();
    }

    @PostMapping("/video")
    public R addVideo(@RequestParam("file") MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        System.out.println("originalFilename----" + originalFilename);
        try {
            InputStream inputStream = file.getInputStream();
            System.out.println("inputStream----" + inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            byte[] bytes = file.getBytes();
            System.out.println("bytes----" + bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String contentType = file.getContentType();
        System.out.println("contentType----" + contentType);
        String name = file.getName();
        System.out.println("name----" + name);
        Resource resource = file.getResource();
        System.out.println("resource----" + resource);
        long size = file.getSize();
        System.out.println("size----" + size);
        /*file.transferTo();*/


        // 调用 COS 接口之前必须保证本进程存在一个 COSClient 实例，如果没有则创建
// 详细代码参见本页：简单操作 -> 创建 COSClient
        // COSClient cosClient = createCOSClient();

// 存储桶的命名格式为 BucketName-APPID，此处填写的存储桶名称必须为此格式
        String bucketName = "zwe-api-1258360747";
// 对象键(Key)是对象在存储桶中的唯一标识。


        String url = "";
//这里修改一下文件名字
        String oldFileName = file.getOriginalFilename();
        String eName = oldFileName.substring(oldFileName.lastIndexOf("."));
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = formatter.format(date);
        //新名字
        String newFileName = time + eName;
        String key = "899322144639270/video/"  + newFileName;
        // 简单文件上传, 最大支持 5 GB, 适用于小文件上传
        // 大文件上传请参照 API 文档高级 API 上传
        File localFile = null;
        try {
            localFile = File.createTempFile("temp", null);
            file.transferTo(localFile);
            // 指定要上传到 COS 上的路径
            String KEY = "video/" + newFileName;
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            // putobjectResult会返回文件的etag
            URL objectUrl = cosClient.getObjectUrl(bucketName, key);//线上地址URL
            url = objectUrl.toString();
            System.out.println(url);
            return R.ok().put("url", url);
        } catch (IOException e) {
            return R.error("上传出错!" + e);
        } finally {
            // 关闭客户端(关闭后台线程)
            cosClient.shutdown();
        }



    }


    @PostMapping("/video2")
    public R addVideo2(@RequestParam("file") MultipartFile file) {
        String bucketName = "zwe-api-1258360747";
        String key = "899322144639270/video/--tesr" + file.getOriginalFilename() ;

        int inputStreamLength = Math.toIntExact(file.getSize());
        byte data[] = new byte[inputStreamLength];
        InputStream inputStream = new ByteArrayInputStream(data);

        ObjectMetadata objectMetadata = new ObjectMetadata();
// 上传的流如果能够获取准确的流长度，则推荐一定填写 content-length
// 如果确实没办法获取到，则下面这行可以省略，但同时高级接口也没办法使用分块上传了
        objectMetadata.setContentLength(inputStreamLength);
        InputStream inputStream1 = null;
        try {
            inputStream1 = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream1, objectMetadata);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        System.out.println(putObjectResult.getRequestId());
        return R.ok();
    }
}
