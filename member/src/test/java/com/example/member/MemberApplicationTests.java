package com.example.member;

import com.example.member.Utils.encrption;
import com.example.member.mapper.TestMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class MemberApplicationTests {

    //记录器
    Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${spring.redis.host}")
    private String host;

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    @Autowired
    JedisPool jedisPool;

    @Test
    void contextLoads() {
    }

    @Test
    public void test11(){
//        SqlSession sqlSession = sqlSessionFactory.openSession();
//        TestMapper mapper = sqlSession.getMapper(TestMapper.class);
//        System.out.println(mapper.test1());
//        System.out.println(encrption.multiAndSalt("123456"));
        Jedis jedis = jedisPool.getResource();
        String set = jedis.set("key2", "you");
        System.out.println(set);
        jedis.close();
    }

    /**
     * redis是一款开源的Key-Value数据库，运行在内存中，由C语言编写。企业开发通常采用Redis来实现缓存。
     * Jedis是Redis官方推出的一款面向Java的客户端，提供了很多接口供Java语言调用
     * Spring-data-redis是spring大家族的一部分，提供了在srping应用中通过简单的配置访问redis服务，对reids底层开发包(Jedis, JRedis, and RJC)进行了高度封装，RedisTemplate提供了redis各种操作、异常处理及序列化，支持发布订阅，并对spring 3.1 cache进行了实现。
     */

    /**
     * Jedis测试单机版
     */
    @Test
    public void testRedisSingle(){
        Jedis jedis = new Jedis("localhost",6379);
        String set = jedis.set("key1", "hello");
        System.out.println(set);
        String key1 = jedis.get("key1");
        System.out.println(key1);
        Long key11 = jedis.del("key1");
        System.out.println(key11);
        jedis.close();

    }

    /**
     * 使用连接池连接
     */
    @Test
    public void testJedisPool(){
        JedisPool jedisPool = new JedisPool("localhost",6379);
        Jedis jedis = jedisPool.getResource();
        String set = jedis.set("key2", "you");
        System.out.println(set);
        jedis.close();
    }


    /**
     * 测试集群
     * @throws IOException
     */
    public static void testJediCluster() throws IOException {
        //创建HostAndPort:集群中的一个节点
        Set<HostAndPort> nodes =new HashSet<>();
        nodes.add(new HostAndPort("192.168.116.130",8001));
        nodes.add(new HostAndPort("192.168.116.130",8002));
        nodes.add(new HostAndPort("192.168.116.130",8003));
        nodes.add(new HostAndPort("192.168.116.130",8004));
        nodes.add(new HostAndPort("192.168.116.130",8005));
        nodes.add(new HostAndPort("192.168.116.130",8006));
        //创建操作集群jedis对象
        JedisCluster jc = new JedisCluster(nodes);
        jc.set("jedis","Hello");
        String str = jc.get("jedis");
        System.out.println(str);
        jc.close();
    }

}
