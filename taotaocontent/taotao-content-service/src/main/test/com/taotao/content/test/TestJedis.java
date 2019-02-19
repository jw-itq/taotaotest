package com.taotao.content.test;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class TestJedis {

    @Test
    public void testJedis(){
        Jedis jedis = new Jedis("192.168.122.1",6379);
        jedis.set("test","hahahh");
        String str = jedis.get("test");
        System.out.println(str);
        jedis.close();
    }

    @Test
    public void testJedisCluster() throws IOException {
        Set<HostAndPort> set = new HashSet<HostAndPort>();
        set.add(new HostAndPort("127.0.0.1",7001));
        set.add(new HostAndPort("127.0.0.1",7002));
        set.add(new HostAndPort("127.0.0.1",7003));
        set.add(new HostAndPort("127.0.0.1",7004));
        set.add(new HostAndPort("127.0.0.1",7005));
        set.add(new HostAndPort("127.0.0.1",7006));

        JedisCluster jedisCluster = new JedisCluster(set);

        jedisCluster.set("keytest1","happy birthday");
        String str = jedisCluster.get("keytest1");
        System.out.println(str);

        jedisCluster.close();
    }

}
