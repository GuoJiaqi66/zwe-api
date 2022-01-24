package top.zwsave.zweapi.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: Ja7
 * @Date: 2022-01-23 11:02
 */
public interface VideoService {

    String newVideo(String token, MultipartFile file, String text, String visible);
}
