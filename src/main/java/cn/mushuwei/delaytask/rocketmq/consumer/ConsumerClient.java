package cn.mushuwei.delaytask.rocketmq.consumer;

import cn.mushuwei.delaytask.rocketmq.config.MqConfig;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.bean.ConsumerBean;
import com.aliyun.openservices.ons.api.bean.Subscription;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author james mu
 * @date 2020/8/23 16:22
 */
@Configuration
public class ConsumerClient {

    private final MqConfig mqConfig;
    private final TicketMessageListener ticketMessageListener;

    public ConsumerClient(MqConfig mqConfig, TicketMessageListener ticketMessageListener) {
        this.mqConfig = mqConfig;
        this.ticketMessageListener = ticketMessageListener;
    }

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public ConsumerBean buildConsumer() {
        ConsumerBean consumerBean = new ConsumerBean();
        //配置文件
        Properties properties = mqConfig.getMqPropertie();
        properties.setProperty(PropertyKeyConst.GROUP_ID, mqConfig.getTicketGroupId());
        //将消费者线程数固定为20个 20为默认值
        properties.setProperty(PropertyKeyConst.ConsumeThreadNums, "20");
        //订阅关系
        Map<Subscription, MessageListener> subscriptionTable = new HashMap<Subscription, MessageListener>();
        Subscription subscription = new Subscription();
        subscription.setTopic(mqConfig.getTicketTopic());
        subscription.setExpression(mqConfig.getTicketTag());
        subscriptionTable.put(subscription, ticketMessageListener);
        //订阅多个topic如上面设置
        consumerBean.setSubscriptionTable(subscriptionTable);
        consumerBean.setProperties(properties);
        return consumerBean;
    }

}
