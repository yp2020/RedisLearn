package com.study.scanTest;

import com.study.fengzhuang.Redis;

/**
 * @author yang
 * @date 2022/02/02 21:35
 **/
public class ScanTest{
    public static void main(String[] args) {
        Redis redis=new Redis();
        redis.execute(jedis -> {
            for(int i=0;i<100000;i++){
                jedis.set("k"+i,"v"+i);
            }
        });
        System.out.println("数据插入完毕");
    }
}