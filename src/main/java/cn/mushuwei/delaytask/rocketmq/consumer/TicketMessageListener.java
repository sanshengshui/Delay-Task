package cn.mushuwei.delaytask.rocketmq.consumer;


import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author james mu
 * @date 2020/8/23 16:19
 */
@Slf4j
@Component
public class TicketMessageListener implements MessageListener {

    @Override
    public Action consume(Message message, ConsumeContext consumeContext) {
        log.info("Receive: " + message);
        try {
            return Action.CommitMessage;
        } catch (Exception e) {
            return Action.ReconsumeLater;
        }
    }
}
