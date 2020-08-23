package cn.mushuwei.delaytask.rocketmq.producer;

import cn.mushuwei.delaytask.rocketmq.config.MqConfig;
import com.aliyun.openservices.ons.api.bean.ProducerBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author james mu
 * @date 2020/8/23 16:14
 */
@Configuration
public class TicketProducerClient {

    private final MqConfig mqConfig;

    public TicketProducerClient(MqConfig mqConfig) {
        this.mqConfig = mqConfig;
    }

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public ProducerBean buildProducer() {
        ProducerBean producer = new ProducerBean();
        producer.setProperties(mqConfig.getMqPropertie());
        return producer;
    }
}
