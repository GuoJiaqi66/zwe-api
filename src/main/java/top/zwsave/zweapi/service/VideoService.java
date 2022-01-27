package top.zwsave.zweapi.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author: Ja7
 * @Date: 2022-01-23 11:02
 */
public interface VideoService {

    String newVideo(String token, MultipartFile file, String text, String visible);

    List selectByPage(int pageNum, int pageSize);

    Integer deleteVideo(Long id);

    String selectVisibleById(Long id);

    Integer likeVideo(String token, Long id);
}
