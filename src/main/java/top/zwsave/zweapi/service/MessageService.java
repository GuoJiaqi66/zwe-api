package top.zwsave.zweapi.service;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: Ja7
 * @Date: 2022-01-12 18:30
 */
public interface MessageService {

    HashMap getPersonalMsg(String token);

    void updateReadFlag(String uuid);

//    List selectAllNotReadPersonalMsg(String token);
}
