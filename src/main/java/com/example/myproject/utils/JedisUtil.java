package com.example.myproject.utils;


import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * redis Api操作工具类
 *
 * @author HUANG
 * @create 2016/12/20 16:32
 */
@SuppressWarnings("ALL")
public class JedisUtil {

    public static String set(Jedis jedis, String redisKey, String value) {
        return jedis.set(redisKey, value);
    }

    public static Long expire(Jedis jedis, String redisKey, Integer seconds) {
        return jedis.expire(redisKey, seconds);
    }

    public static Boolean exists(Jedis jedis, String redisKey) {
        return jedis.exists(redisKey);
    }

    /**
     * 操作 hash API  start
     */
    public static Long hset(Jedis jedis, String redisKey, String field, String value) {
        return jedis.hset(redisKey, field, value);
    }

    public static void hset(Pipeline pp, String redisKey, String field, String value) {
        pp.hset(redisKey, field, value);
    }

    public static void hdel(Pipeline pp, String redisKey, String field) {
        pp.hdel(redisKey, field);
    }

    public static Long hdel(Jedis jedis, String redisKey, String field) {
        return jedis.hdel(redisKey, field);
    }

    public static Long hlen(Jedis jedis, String redisKey) {
        return jedis.hlen(redisKey);
    }

    public static Map<String, String> hgetAll(Jedis jedis, String redisKey) {
        return jedis.hgetAll(redisKey);
    }

    /**操作set hash  end*/

    /**
     * 操作 set API  start
     */
    public static Long sadd(Jedis jedis, String redisKey, String value) {
        return jedis.sadd(redisKey, value);
    }

    public static void sadd(Pipeline pp, String redisKey, String value) {
        pp.sadd(redisKey, value);
    }

    public static void srem(Pipeline pp, String redisKey, String value) {
        pp.srem(redisKey, value);
    }

    public static Long srem(Jedis jedis, String redisKey, String value) {
        return jedis.srem(redisKey, value);
    }

    public static Long scard(Jedis jedis, String redisKey) {
        return jedis.scard(redisKey);
    }

    public static Set<String> redisKey(Jedis jedis, String redisKey) {
        return jedis.smembers(redisKey);
    }

    /**
     * 操作 set API  end
     */

    public static Long incrBy(Jedis jedis, String key, int integer) {
        return jedis.incrBy(key, integer);
    }

    public static Long incrBy(String key, int integer) {
        Jedis jedis = null;
        try {
            jedis = JedisUtil.getJedis();
            Long number = jedis.incrBy(key, integer);
            return number;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            close(jedis);
        }
    }

    public static Response<Long> incrBy(Pipeline pp, String key, int integer) {
        return pp.incrBy(key, integer);
    }

    public static Jedis getJedis() {
        return JedisConnectionPool.getJdsConnection();
    }

    public static void close(Jedis jedis) {
        if (jedis != null)
            jedis.close();
    }

    public static void close(Pipeline pp) {
        if (pp != null)
            try {
                pp.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

    /**
     * 根据正则获取value
     *
     * @param jedis
     * @param keyRe key正则  keys *
     * @return Map[String, String] Key为redis Key Value为key对应的value
     */
    public static Map<String, String> queryByRegex(Jedis jedis, String keyRe) {
        //将要查询的Keys
        Set<String> keys = getKeys(jedis, keyRe);
        //返回给客户端的借结果
        Map<String, String> result = new HashMap<String, String>();
        //批量获取
        Pipeline pp = jedis.pipelined();
        //
        Iterator<String> iter = keys.iterator();

        Map<String, Response<String>> resposes = new HashMap<String, Response<String>>();

        while (iter.hasNext()) {
            String key = iter.next();
            Response<String> response = pp.get(key);
            resposes.put(key, response);
        }
        pp.sync();
        for (String key : resposes.keySet()) {
            Response<String> respose = resposes.get(key);
            result.put(key, respose.get());
        }
        return result;
    }

    public static Map<String, String> queryByRegex(String keyRe) {
        Jedis jedis = null;
        try {
            jedis = JedisUtil.getJedis();
            Map<String, String> result = queryByRegex(jedis, keyRe);
            return result;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            close(jedis);
        }

    }

    public static void deleteByRegex(Jedis jedis, String keyRe) {
        Set<String> keys = getKeys(jedis, keyRe);
        Pipeline pp = jedis.pipelined();
        Iterator<String> iter = keys.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
            pp.del(key);
        }
        pp.sync();
    }

    public static void del(Jedis jedis, String key) {
        jedis.del(key);
    }

    public static void deleteByRegex(String keyRe) {
        Jedis jedis = null;
        try {
            jedis = JedisUtil.getJedis();
            deleteByRegex(jedis, keyRe);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            close(jedis);
        }
    }

    public static Set<String> getKeys(Jedis jedis, String keyRe) {
        return jedis.keys(keyRe);
    }


    public static void setExpire(String key, String value, int expireTime) {
        if (StringUtils.isEmpty(key)) {
            throw new RuntimeException("set redis key  not null!");
        }
        if (StringUtils.isEmpty(value)) {
            throw new RuntimeException("set redis value  not null!");
        }
        Jedis jedis = null;
        try {
            jedis = JedisUtil.getJedis();
            String setex = jedis.setex(key, expireTime, value);
        } finally {
            close(jedis);
        }
    }


}
