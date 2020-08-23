package cn.mushuwei.delaytask.rocketmq.config;

import com.aliyun.openservices.ons.api.PropertyKeyConst;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author james mu
 * @date 2020/8/23 15:57
 */
@Configuration
@ConfigurationProperties(prefix = "ons")
@Data
public class MqConfig {

    private String accessKey;
    private String secretKey;
    private String nameSrvAddr;

    private String ticketTopic;
    private String ticketGroupId;
    private String ticketTag;

    public Properties getMqPropertie() {
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.AccessKey, this.accessKey);
        properties.setProperty(PropertyKeyConst.SecretKey, this.secretKey);
        properties.setProperty(PropertyKeyConst.NAMESRV_ADDR, this.nameSrvAddr);
        return properties;
    }
}
