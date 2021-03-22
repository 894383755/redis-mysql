package com.kd.xxhyf.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.JedisCommands;

import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTest {
    private String key = "GuoDiao_PROCESSMODEL";

    @Qualifier("getJedisCommands")
    @Autowired
    private JedisCommands jedis;

    @Test
    public void getRedisKey() {
        if( jedis.exists(key)){
            Map<String, String> stringStringMap = jedis.hgetAll(key);
            System.out.println(stringStringMap);
        }

    }
}
