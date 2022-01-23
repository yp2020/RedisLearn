package com.study.delayqueue;

import com.study.fengzhuang.Redis;

/**
 * @author yang
 * @date 2022/01/17 21:52
 **/
public class DelayMsgTest {
    public static void main(String[] args) {
        Redis redis=new Redis();
        redis.execute(jedis -> {

            //构造消息队列
            DelayMsgQueue delayMsgQueue = new DelayMsgQueue(jedis, "delay-queue-key");

            //构造消息生产者
            Thread producer =new Thread(){
                @Override
                public void run() {
                    for(int i=0;i<5;i++){
                        delayMsgQueue.InQueue("这是第"+i+"条消息,具体内容是。。。。");
                    }
                }
            };

            //构造消息消费者

            Thread consumer =new Thread(){
                @Override
                public void run() {
                    delayMsgQueue.loopQueue();
                }
            };

            producer.start();
            consumer.start();
            try{
                Thread.sleep(10000);
                consumer.interrupt();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}