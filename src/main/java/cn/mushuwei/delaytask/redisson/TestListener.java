package cn.mushuwei.delaytask.redisson;

import cn.mushuwei.delaytask.redisson.model.TaskBodyDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author james mu
 * @date 2020/8/23 23:28
 */
@Slf4j
@Component
public class TestListener  implements RedisDelayedQueueListener<TaskBodyDTO> {

    @Override
    public void invoke(TaskBodyDTO taskBodyDTO) {
        //这里调用你延迟之后的代码
        log.info("执行...." + taskBodyDTO.getBody() + "===" + taskBodyDTO.getName());
    }
}
