package cn.mushuwei.delaytask.redisson.model;

import java.io.Serializable;

/**
 * @author james mu
 * @date 2020/8/23 23:27
 */
public class TaskBodyDTO implements Serializable {

    private String name;
    private String body;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
