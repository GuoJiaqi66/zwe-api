package top.zwsave.zweapi.service;

import org.springframework.web.multipart.MultipartFile;
import top.zwsave.zweapi.controller.form.AddArticleForm;

import java.util.List;

/**
 * @Author: Ja7
 * @Date: 2022-01-21 11:57
 */
public interface ArticleService {

    String insert(MultipartFile file,AddArticleForm article, String token);

    List selectByPage(int pageNum, int pageSize);

    Integer deleteArticle(Long id);

    String selectVisibleById(Long id);

    Integer likeArticle(String token, Long id);

    Integer removeLikeArticle(String token, Long id);

    Integer starArticle(String token, Long id);

    Integer removeStarArticle(String token, Long id);

    Integer articleLook(String token, Long id);
}
