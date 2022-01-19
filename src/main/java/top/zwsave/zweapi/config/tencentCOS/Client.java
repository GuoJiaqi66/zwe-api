package top.zwsave.zweapi.config.tencentCOS;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.region.Region;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @Author: Ja7
 * @Date: 2022-01-18 16:58
 */
@Configuration
@EnableConfigurationProperties(Properties.class)
public class Client {

    @Resource
    Properties properties;

    @Bean
    public COSClient cosClient() {
        BasicCOSCredentials cred = new BasicCOSCredentials(properties.getSecretId(), properties.getSecretKey());
        Region region = new Region(properties.getRegionName());
        ClientConfig config = new ClientConfig(region);
        COSClient client = new COSClient(cred, config);
        return client;
    }

}
