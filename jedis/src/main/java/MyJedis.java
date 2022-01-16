import redis.clients.jedis.Jedis;

import java.nio.charset.StandardCharsets;

public class MyJedis {
    public static void main(String[] args) {
        //1. 构造对象，
        Jedis jedis = new Jedis("47.98.151.231");
        //2.密码认证
       jedis.auth("y123p456");
        //3.测试是否连接成功
        String ping =jedis.ping();
        //4.返回 pong 表示连接成功
        System.out.println(ping);
        //连接上以后，
        String k1="k1";

        jedis.set("k1","12");
        System.out.println(jedis.get(k1));
        System.out.println(jedis.get("k2"));
    }
}
