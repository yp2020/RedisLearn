package com.study.distributed_lock;

/**
 * @author yang
 * @date 2022/01/16 21:06
 **/
public class LockTest {
    public static void main(String[] args) {
        Redis redis = new Redis();
        redis.execute(jedis -> {
            Long setnx = jedis.setnx("k1", "v1");
            if(setnx==1){
                //正常情况下，返回 1, 表示不占位
                jedis.set("money", String.valueOf(10000));
                System.out.println(jedis.get("money"));
                // 释放资源
                jedis.del("k1");

            }else{
                //说明有其他线程占位置，需要停止操作
            }
        });
    }
}