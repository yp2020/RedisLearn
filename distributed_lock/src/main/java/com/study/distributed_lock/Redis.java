package com.study.distributed_lock;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class Redis {
    private JedisPool pool;
    public Redis(){
        GenericObjectPoolConfig config=new GenericObjectPoolConfig();

        //连接池最大的空闲数
        config.setMaxIdle(300);
        //连接池最大空闲数
        config.setMaxIdle(300);
        //最大连接数
        config.setMaxTotal(1000);
        //连接最大等待时间，如果是 -1 表示没有限制
        config.setMaxWaitMillis(30000);
        //在空闲时检查有效性
        config.setTestOnBorrow(true);

                                       // redis 地址 端口，超时时间，密码
        pool=new JedisPool(config,"47.98.151.231",6379,300,"y123p456");

    }
    public void execute(CallWithJedis callWithJedis){
        try(Jedis jedis=pool.getResource()){
            callWithJedis.call(jedis);
        }
    }

    public static void main(String[] args) {
        Redis redis=new Redis();
        redis.execute(jedis -> {
            System.out.println(jedis.ping());
        });
    }

}
