package com.sydml.authorization;

import com.sydml.common.utils.StreamUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorizationApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void contextLoads() {
        redisTemplate.opsForHash().putIfAbsent("hash", "test", "value");
        redisTemplate.opsForHash().putIfAbsent("hash", "test01", "value01");
        redisTemplate.opsForList().rightPush("lsit", "java");
    }

    @Test
    public void testHash() {
        redisTemplate.opsForHash().put("expire", "test", "test01");
        redisTemplate.expire("expire", 50, TimeUnit.SECONDS);
    }

    @Test
    public void testString() {
        redisTemplate.opsForValue().set("string", "value");
        redisTemplate.opsForValue().set("string", "value1");
    }

    @Test
    public void testLua() {

    }

}
