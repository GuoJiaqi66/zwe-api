package top.zwsave.zweapi.service;

import org.springframework.web.multipart.MultipartFile;
import top.zwsave.zweapi.controller.form.PageReq;

import java.util.ArrayList;
import java.util.HashMap;
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

    Integer removeLikeVideo(String token, Long id);

    Integer starVideo(String token, Long id);

    Integer removeStarVideo(String token, Long id);

    Integer videoLook(String token, Long id);

    ArrayList<HashMap> selectAllVideoLooker(String token, Long id);

    List selectMyVideo(String token, PageReq pageReq);

    List selectAllLike(String token, PageReq pageReq);

    List selectAllStar(String token, PageReq pageReq);

    List selectVideoLiker(Long id, PageReq pageReq);

    List selectVideoStarer(Long id, PageReq pageReq);
}
