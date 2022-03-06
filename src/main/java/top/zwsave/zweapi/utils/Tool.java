package top.zwsave.zweapi.utils;

import cn.hutool.core.util.RandomUtil;
import org.springframework.stereotype.Component;

/**
 * @Author: Ja7
 * @Date: 2022-03-06 21:44
 */
@Component
public class Tool {

    public Long uuidLong() {
        String s = RandomUtil.randomNumbers(15).trim();
        long l = Long.parseLong(s);
        return l;
    }

    public String uuidString() {
        String s = RandomUtil.randomNumbers(15).trim();
        return s;
    }

}
