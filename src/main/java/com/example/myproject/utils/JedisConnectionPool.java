package com.example.myproject.utils;


import com.example.myproject.common.CommonConst;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * redis连接池
 *
 * @author HUANG
 * @create 2016/12/20 16:18
 */
public class JedisConnectionPool {
    
    private static Properties conf = new Properties();
    private static JedisPoolConfig jdsConf = new JedisPoolConfig();
    private static JedisPool jedisPool  = null;
    
    static {
        try {
            if(JedisConnectionPool.class.getClassLoader().getResource(CommonConst.REDIS_PROP_NAME) == null){
                conf.load(new FileInputStream(CommonConst.REDIS_PROP_NAME));
            }else{
                InputStream in = JedisConnectionPool.class.getClassLoader().getResourceAsStream(CommonConst.REDIS_PROP_NAME);
                conf.load(in);
            }
           
            String host = conf.getProperty(CommonConst.HOST) ; //the redis host
            Integer port= Integer.parseInt(conf.getProperty(CommonConst.PORT)); //the redis port
            String password = conf.getProperty(CommonConst.PASSWORD); //the redis password
            jdsConf.setMaxTotal(300);
            jdsConf.setMaxIdle(30);
            jdsConf.setTestOnBorrow(true);
            Integer dbindex = Integer.parseInt(conf.getProperty("dbindex", "0"));//默认0号库
            if (null == password || "".equals(password)){
                jedisPool = new JedisPool(jdsConf, host, port, 3000,null,dbindex);
            }else {
                jedisPool = new JedisPool(jdsConf, host,port, 3000,password,dbindex);
            }
            
            //虚拟机关闭的时候关闭连接池
            Runtime.getRuntime().addShutdownHook(new Thread(){
                @Override
                public void run() {
                    jedisPool.destroy();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            //配置文件读取失败，程序接着下去也是没有意义的
            System.exit(-1);
        }
    }

              
    
    
    public static Jedis getJdsConnection() {
        Jedis jedis = jedisPool.getResource();
        return jedis;
    }
    
}
