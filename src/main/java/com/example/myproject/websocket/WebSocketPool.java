package com.example.myproject.websocket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/**
 * web socket 池
 */
public class WebSocketPool {

    private static Log log = LogFactory.getLog(WebSocketPool.class);
    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private ReadLock rl = rwl.readLock();
    private WriteLock wl = rwl.writeLock();
    private AtomicInteger number = new AtomicInteger(0);

    private final static WebSocketPool instance = new WebSocketPool();

    private static HashMap<String, WebSocketEndpoint> connectionsMap = new HashMap<String, WebSocketEndpoint>();

    private WebSocketPool() {
    }

    public static WebSocketPool getInstance() {
        return instance;
    }

    public String getCount() {
        return number.getAndIncrement() + "";
    }

    // 添加连接
    public void addConn(String token, WebSocketEndpoint endpoint) {
        wl.lock();
        try {
            try {
                WebSocketEndpoint webSocketEndpoint = connectionsMap.get(token);
                if(webSocketEndpoint != null){
                    Session session = webSocketEndpoint.getSession();
                    if(session.isOpen()){
                        session.close();
                    }
                }
            }catch (Exception ex){
                //不做处理
            }
            log.info("添加连接，token=" + token);
            connectionsMap.put(token, endpoint);
        } finally {
            wl.unlock();
        }
    }

    // 删除连接
    public void deleteConn(String token) {
        wl.lock();
        try {
            log.info("关闭连接，token=" + token);
            WebSocketEndpoint remove = connectionsMap.remove(token);
            try {
                if(remove != null){
                    Session session = remove.getSession();
                    if(session.isOpen()){
                        session.close();
                    }
                }
            } catch (Exception e) {
                //e.printStackTrace();
            }
        } finally {
            wl.unlock();
        }
    }
    
    // 获取连接
    public WebSocketEndpoint getWebSocketEndpoint(String token) {
        rl.lock();
        WebSocketEndpoint bean = null;
        try {
            bean = connectionsMap.get(token);
        } finally {
            rl.unlock();
        }
        return bean;
    }
    
    /**
     * 发送websocket 消息
     * @param token
     * @param message
     */
    public void sendMessage(String token, String message) {
        
        WebSocketEndpoint socketEndpoint = getWebSocketEndpoint(token);
        if (null != socketEndpoint) {
            try {
                socketEndpoint.sendMessage(message);
            } catch (Exception e) {
                log.error("WebSocketPool class sendMessage is error : " + e.toString());
            }
            log.info("发送消息，token=" + token + ",msg=" + message);
        }
    }
    
    /**
     * kill WebSocketEndpoint
     * @param token
     */
    public void killWebSocketEndpoint(String token) {
        
        WebSocketEndpoint socketEndpoint = getWebSocketEndpoint(token);
        if (null != socketEndpoint) {
            try {
                socketEndpoint.getSession().close();
            } catch (IOException e) {
                log.info("kill websocket终端，token=" + token + ",msg=success");
            }
            log.info("kill websocket终端，token=" + token + ",msg=fail");
        }
    }
    
    public static void main(String[] args) {
        WebSocketPool.getInstance().addConn("TOKEN:21232f297a57a5a743894a0e4a801fc3",new WebSocketEndpoint());
        WebSocketPool.getInstance().sendMessage("TOKEN:21232f297a57a5a743894a0e4a801fc3","");
    }
}
