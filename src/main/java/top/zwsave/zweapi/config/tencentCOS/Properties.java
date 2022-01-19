package top.zwsave.zweapi.config.tencentCOS;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: Ja7
 * @Date: 2022-01-18 16:56
 */
@Data
@ConfigurationProperties(prefix = "tencent")
public class Properties {

    private String secretId;

    private String secretKey;

    private String bucket;

    private String regionName;
}
