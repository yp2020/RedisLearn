package com.study.distributed_lock;

import com.study.fengzhuang.Redis;
import redis.clients.jedis.params.SetParams;

/**
 * @author yang
 * @date 2022/01/16 21:06
 **/
public class LockTest {
    public static void main(String[] args) {
      Redis redis=new Redis();
      redis.execute(jedis -> {
          Long setnx=jedis.setnx("k1","v1");
          if(setnx==1){
              jedis.set("name","ggb");
              String name = jedis.get("name");
              System.out.println("name: "+ name);
              jedis.del("name");
          }else{
              System.out.println("有人占位停止操作");
          }
      });
    }
}