package top.zwsave.zweapi.service;

import org.springframework.web.multipart.MultipartFile;
import top.zwsave.zweapi.controller.form.AddArticleForm;

/**
 * @Author: Ja7
 * @Date: 2022-01-21 11:57
 */
public interface ArticleService {

    String insert(MultipartFile file,AddArticleForm article, String token);

}
