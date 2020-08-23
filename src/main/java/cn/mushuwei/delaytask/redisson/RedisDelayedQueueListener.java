package cn.mushuwei.delaytask.redisson;

/**
 * 队列事件监听接口，需要实现这个方法
 *
 * @author james mu
 * @date 2020/8/23 23:26
 */
public interface RedisDelayedQueueListener<T> {

    /**
     * 执行方法
     *
     * @param t
     */
    void invoke(T t);
}
