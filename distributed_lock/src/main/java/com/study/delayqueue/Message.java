package com.study.delayqueue;

/**
 * 消息对象
 * @author yang
 * @date 2022/01/17 21:42
 **/
public class Message {
    private String id ;
    private Object data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", data=" + data +
                '}';
    }
}