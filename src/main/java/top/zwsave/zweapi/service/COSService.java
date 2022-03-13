package top.zwsave.zweapi.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: Ja7
 * @Date: 2022-01-18 17:28
 */
public interface COSService {

    String uploadFaceImg(Long userId, MultipartFile file);

    String insertArticleImg(MultipartFile file, String token, Long id);

    String insertVideo(MultipartFile file, String token, Long id);

    String insertSystemVideoMsg(MultipartFile file);
}
