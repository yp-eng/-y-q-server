package com.example.myproject.utils;

import com.example.myproject.entity.TbSysAccount;
import com.example.myproject.websocket.WebSocketPool;
import redis.clients.jedis.Jedis;

import java.util.Iterator;
import java.util.Set;

/**
 * 系统提示的websocketUtil
 *
 * @author HUANG
 * @create 2017/05/24 9:47
 */
public class SysWebSocketUtil {
    
    /**
     * 给终端发送消息，发送完毕后kill内存中对应的终端session信息
     * @param account 账号信息对象
     * @param message 消息
     * @param jedis redis客户端
     */
    public static void sendMessagedKill(TbSysAccount account, String message, Jedis jedis) {
        String prefixToken = null;// AccountUtil.biudPrefixToken(account);
        Set<String> keys = null;
        keys = JedisUtil.getKeys(jedis, prefixToken);
        if (keys != null && keys.size() > 0) {
            Iterator<String> iterator = keys.iterator();
            while (iterator.hasNext()) {
                String fullTokenStr = iterator.next();
                //发送退出标记
                WebSocketPool.getInstance().sendMessage(fullTokenStr, message);
                //从连接池中删除websoket终端
                WebSocketPool.getInstance().killWebSocketEndpoint(fullTokenStr);
            }
        }
    }
    
    /**
     * 给终端发送消息，发送完毕后kill内存中对应的终端session信息
     * @param account 账号信息对象
     * @param message 消息
     */
    public static void sendMessagedKill(TbSysAccount account,String message) {
        Jedis jedis = null;
        try {
            jedis =  JedisUtil.getJedis();
            sendMessagedKill(account,message,jedis);
        }catch (Exception ex){
            throw ex;
        }finally {
            if(jedis != null)
                jedis.close();
        }
    }
    
    /**
     * 给终端发送消息
     * @param account 账号信息对象
     * @param message 消息
     * @param jedis redis客户端
     */
    public static void sendMessage(TbSysAccount account, String message, Jedis jedis) {
        String prefixToken = null ;// AccountUtil.biudPrefixToken(account);
        Set<String> keys = null;
        keys = JedisUtil.getKeys(jedis, prefixToken);
        if (keys != null && keys.size() > 0) {
            Iterator<String> iterator = keys.iterator();
            while (iterator.hasNext()) {
                String fullTokenStr = iterator.next();
                //发送退出标记
                WebSocketPool.getInstance().sendMessage(fullTokenStr, message);
            }
        }
    }
    
    /**
     * kill 掉指定token的终端session信息
     * @param token
     */
    public static void KillWebSocketEndpoint(String token) {
        WebSocketPool.getInstance().killWebSocketEndpoint(token);
    }
    
}
