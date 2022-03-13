package top.zwsave.zweapi.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: Ja7
 * @Date: 2022-03-13 12:41
 */
public interface SystemMsgService {

    String newSystemVideoMsg(String token, MultipartFile file, String text);
}
