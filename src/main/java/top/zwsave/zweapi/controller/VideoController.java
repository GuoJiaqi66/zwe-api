package top.zwsave.zweapi.controller;

import io.swagger.annotations.Api;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.zwsave.zweapi.common.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: Ja7
 * @Date: 2022-01-22 14:43
 */
@RestController
@Api("视频接口")
public class VideoController {

    @PostMapping("/video")
    public R addVideo(@RequestParam("file") MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        System.out.println("originalFilename" + originalFilename);
        try {
            InputStream inputStream = file.getInputStream();
            System.out.println("inputStream" + inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            byte[] bytes = file.getBytes();
            System.out.println("bytes" + bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String contentType = file.getContentType();
        System.out.println("contentType" + contentType);
        String name = file.getName();
        System.out.println("name" + name);
        Resource resource = file.getResource();
        System.out.println("resource" + resource);
        long size = file.getSize();
        System.out.println("size" + size);
        return R.ok();
    }
}
