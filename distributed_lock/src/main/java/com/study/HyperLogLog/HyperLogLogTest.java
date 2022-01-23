package com.study.HyperLogLog;

import com.study.fengzhuang.Redis;

/**
 * @author yang
 * @date 2022/01/23 15:26
 **/
public class HyperLogLogTest {
    public static void main(String[] args) {
        Redis redis=new Redis();
        redis.execute(jedis -> {
            for(int i=0;i<1000;i++){
                jedis.pfadd("uv","u"+i,"u"+(i+1));
            }
            long uv = jedis.pfcount("uv");
            System.out.println(uv);
        });
    }
}