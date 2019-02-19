package com.taotao.content.test;

import com.taotao.jedis.JedisClient;
import com.taotao.jedis.JedisClientPool;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestJedisPool {

    @Test
    public void testJedisClientPool(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
        JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
        jedisClient.set("cluster2","aaaaaaaa");
        String str = jedisClient.get("cluster2");
        System.out.println(str);
    }

}
