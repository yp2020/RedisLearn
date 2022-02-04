package com.study.BloomFilter;

import io.rebloom.client.Client;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.JedisPool;


/**
 * @author yang
 * @date 2022/01/23 20:12
 **/
public class BloomFilter {
    public static void main(String[] args) {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();

        //连接池最大的空闲数
        config.setMaxIdle(300);
        //最大连接数
        config.setMaxTotal(1000);
        //连接最大等待时间，如果是 -1 表示没有限制
        config.setMaxWaitMillis(30000);
        //在空闲时检查有效性
        config.setTestOnBorrow(true);

        JedisPool pool = new JedisPool(
                 config,
                "47.98.151.231",
                6379,
                30000,
                "y123p456");

        Client client = new Client(pool);

        for(int i=0;i<1000;i++){
            client.add("name","JavaBoy-"+i);
        }


        boolean exists = client.exists("name", "JavaBoy-2222");
        System.out.println(exists);

        boolean exists1 = client.exists("name", "JavaBoy-9999999999");
        System.out.println("9999999999 是否存在"+exists1);
    }
}