package com.study.delayqueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * 消息队列
 * @author yang
 * @date 2022/01/17 21:43
 **/

public class DelayMsgQueue {

    private Jedis jedis;
    private String keyQueue;

    public DelayMsgQueue(Jedis jedis, String keyQueue) {
        this.jedis = jedis;
        this.keyQueue = keyQueue;
    }


    /**
     * 发送消息
     * @param data
     */
    public void InQueue(Object data){
        Message msg=new Message();
        msg.setId(UUID.randomUUID().toString());
        msg.setData(data);
        try {
            String s = new ObjectMapper().writeValueAsString(msg);
            System.out.println("发送消息: "+ s+ "发送时间"+new Date());
            // 利用时间做为 分数
            jedis.zadd(keyQueue,System.currentTimeMillis()+5000,s);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


    /**
     * 消息消费
     */
    public void loopQueue(){
        while(!Thread.interrupted()){
            Set<String> zrangeByScore = jedis.zrangeByScore(keyQueue, 0, System.currentTimeMillis(), 0, 1);
            if(zrangeByScore.isEmpty()){
                //啥都没有
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
                continue;
            }

            String next = zrangeByScore.iterator().next();
            if(jedis.zrem(keyQueue,next)>0){
                try {
                    Message msg = new ObjectMapper().readValue(next, Message.class);
                    System.out.println("接收到消息: "+ msg+"接收时间: "+ new Date());
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }


            }
        }
    }
}