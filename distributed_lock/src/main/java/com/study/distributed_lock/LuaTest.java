package com.study.distributed_lock;

import com.study.fengzhuang.Redis;
import redis.clients.jedis.params.SetParams;

import java.util.Arrays;
import java.util.UUID;

/**
 * @author yang
 * @date 2022/01/16 22:01
 **/
public class LuaTest {
    public static void main(String[] args) {
        Redis redis=new Redis();
        int cnt=0;
        for(int i=0;i<2;i++){
            int finalI = i+1;
            redis.execute(jedis -> {
                // 1. 先获取一个随机字符串
                String value = UUID.randomUUID().toString();

                //2. 获取锁
                String k1 = jedis.set("k1", value, new SetParams().nx().ex(10));

                //3.判断锁是否被拿到
                if(k1!=null&&"OK".equals(k1)){
                    //4. 具体的业务操作
                    jedis.set("money","10000000");
                    System.out.println(jedis.get("money"));

                    //5. 释放锁
                    jedis.evalsha("b8059ba43af6ffe8bed3db65bac35d452f8115d8", Arrays.asList("k1"),Arrays.asList(value));
                }else{
                    System.out.println("第"+ finalI + "次获取锁，获取失败");
                }
            });
        }
    }
}