import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisPoolTest {
    public static void main(String[] args) {
        //1. 构造 Jedis 连接池
        JedisPool jedisPool = new JedisPool("47.98.151.231", 6379);

        /**
         * 为了防止异常导致资源无法关闭，使用 try-with-Resources 特性
         */
        //2. 从连接池中获取连接
        try( Jedis myJedis = jedisPool.getResource()){
            myJedis.auth("y123p456");
            String ping=myJedis.ping();
            System.out.println(ping);
            System.out.println(myJedis.get("k1"));
        }
    }
}
