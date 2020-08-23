package cn.mushuwei.delaytask.controller;

import cn.mushuwei.delaytask.redisson.RedisDelayedQueue;
import cn.mushuwei.delaytask.redisson.TestListener;
import cn.mushuwei.delaytask.redisson.model.TaskBodyDTO;
import cn.mushuwei.delaytask.rocketmq.config.MqConfig;
import cn.mushuwei.delaytask.rocketmq.producer.TicketProducerClient;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.bean.ProducerBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author james mu
 * @date 2020/8/23 16:36
 */
@RestController
@Slf4j
public class DelayTaskController {


    private final TicketProducerClient ticketProducerClient;
    private final RedisDelayedQueue delayedQueue;
    private final MqConfig mqConfig;

    public DelayTaskController(TicketProducerClient ticketProducerClient, RedisDelayedQueue delayedQueue, MqConfig mqConfig) {
        this.ticketProducerClient = ticketProducerClient;
        this.delayedQueue = delayedQueue;
        this.mqConfig = mqConfig;
    }

    @RequestMapping(name = "ons延时队列接口验证", value = "/api/delayTask/ons", method = RequestMethod.GET)
    public void onsDelayTask() {
        ProducerBean producerBean = ticketProducerClient.buildProducer();
        producerBean.start();
        Message msg = new Message(mqConfig.getTicketTopic(), mqConfig.getTicketTag(), "Hello Ons".getBytes());
        try {
            long delayTime = 3000 + System.currentTimeMillis();
            msg.setStartDeliverTime(delayTime);
            SendResult sendResult = producerBean.send(msg);
            if (sendResult != null) {
                log.info("Send mq message success. Topic is:" + msg.getTopic() + " msgId is: " + sendResult.getMessageId());
            }
        } catch (Exception e) {
            log.info("Send mq message failed. Topic is:" + msg.getTopic());
            e.printStackTrace();
        }
        producerBean.shutdown();
    }

    @RequestMapping(name = "redisson延时队列接口验证", value = "/api/delayTask/redisson", method = RequestMethod.GET)
    public void redissonDelayTask() {
        TaskBodyDTO taskBody = new TaskBodyDTO();
        taskBody.setBody("测试DTO实体类的BODY,10秒之后执行");
        taskBody.setName("测试DTO实体类的姓名,10秒之后执行");
        //添加队列10秒之后执行
        delayedQueue.addQueue(taskBody, 1000, TimeUnit.MILLISECONDS, TestListener.class.getName());

        taskBody.setBody("测试DTO实体类的BODY,20秒之后执行");
        taskBody.setName("测试DTO实体类的姓名,20秒之后执行");
        //添加队列20秒之后执行
        delayedQueue.addQueue(taskBody, 2000, TimeUnit.MILLISECONDS, TestListener.class.getName());

        taskBody.setBody("测试DTO实体类的BODY,20秒之后执行");
        taskBody.setName("测试DTO实体类的姓名,20秒之后执行");
        //添加队列30秒之后执行
        delayedQueue.addQueue(taskBody, 3000, TimeUnit.MILLISECONDS, TestListener.class.getName());
    }

}
