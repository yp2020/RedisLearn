package com.study.delayqueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.DelayQueue;

/**
 * @author yang
 * @date 2022/01/23 15:07
 **/
public class DelayMsgQueue {

    private  Jedis jedis;
    private String queue;

    public DelayMsgQueue(Jedis jedis,String queue){
        this.jedis=jedis;
        this.queue=queue;
    }

    /**
     * 消息入队列
     * @param data
     */
    public void InQueue(Object data){
        MyMessage msg=new MyMessage();
        msg.setData(data);
        msg.setId(UUID.randomUUID().toString());
        try{
            String s=new ObjectMapper().writeValueAsString(msg);
            System.out.println("消息入队列: "+s+new Date());

            jedis.zadd(queue,System.currentTimeMillis()+5000,s);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


    /**
     * 消息消费
     */
    public void OutQueue(){
        while(!Thread.interrupted()){
            Set<String> zrange= jedis.zrangeByScore(queue,0,System.currentTimeMillis(),0,1);
            if(zrange.isEmpty()){
                try{
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
                continue;
            }
            String next=zrange.iterator().next();
            if(jedis.zrem(queue,next)>0){
                try{
                    MyMessage msg=new ObjectMapper().readValue(next,MyMessage.class);
                    System.out.println("消息接收到了: "+msg);
                } catch (JsonMappingException e) {
                    e.printStackTrace();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}