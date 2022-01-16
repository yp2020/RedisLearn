package com.study.distributed_lock;

import redis.clients.jedis.params.SetParams;

/**
 * @author yang
 * @date 2022/01/16 21:06
 **/
public class LockTest {
    public static void main(String[] args) {
        Redis redis = new Redis();
        redis.execute(jedis -> {
            String set = jedis.set("k1", "v1", new SetParams().nx().ex(5));
            if(set!=null&& "OK".equals(set)){
                jedis.expire("k1",5);
                jedis.set("money", String.valueOf(100000));
                System.out.println("money: "+jedis.get("money"));
                // 释放资源
                jedis.del("k1");
            }
            else{
                //说明有其他线程占位置，需要停止操作
            }
        });
    }
}