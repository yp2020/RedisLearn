package test1;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

public class LettuceTest {
    public static void main(String[] args) {
                                                           //密码和连接地址都写在一起了
        RedisClient redisClient = RedisClient.create("redis://y123p456@47.98.151.231");
        StatefulRedisConnection<String, String> connect = redisClient.connect();
        RedisCommands<String, String> sync = connect.sync();
        sync.set("name","plq");
        String name = sync.get("name");
        System.out.println(name);
    }
}
