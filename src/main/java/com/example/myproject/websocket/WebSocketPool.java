package com.example.myproject.websocket;

import com.alibaba.fastjson.JSON;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.websocket.Session;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
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
    public  static int   count = 2;

    public static void main(String[] args) {
//        WebSocketPool.getInstance().addConn("TOKEN:21232f297a57a5a743894a0e4a801fc3",new WebSocketEndpoint());
//        WebSocketPool.getInstance().sendMessage("TOKEN:21232f297a57a5a743894a0e4a801fc3","");
//        ReadWriteLock lock = new ReentrantReadWriteLock(false);
//
//        Thread threadA = new Thread("threadA"){
//            @Override
//            public void run() {
//                //获取读锁
//                lock.readLock().lock();
//                System.out.println("成功获取读锁，count的值是："+count);
//                if(count<10){
//                    lock.readLock().unlock();
//                    //在获取写锁前，必须先释放读锁
//                    lock.writeLock().lock();
//                    System.out.println("成功获取写锁");
//                    count += count*3;
//
//                    //获取读锁，此时没有释放写锁，即为写锁降级为读锁
//                    lock.readLock().lock();
//                    //成功获取读锁，写锁降级成功，释放写锁
//                    lock.writeLock().unlock();
//                    System.out.println("写锁成功降级成读锁，count的值是："+count);
//                }
//            }
//        };
//        threadA.start();
//
//        int dayYear = LocalDate.now().lengthOfYear();
//
//
//
//        BigDecimal s = new BigDecimal(4.0);
//
//        BigDecimal ss = new BigDecimal(4.0);
//        System.out.println(s.compareTo(ss));
//
//        String str = "a,b,c,,";
//        String[] ary = str.split(",");
//        // 预期大于 3，结果是 3
//        System.out.println(ary.length);
//        System.out.println(JSON.toJSONString(ary));

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String item = iterator.next();
            if ("1".equals(item)) {
                iterator.remove();
            }
        }
        System.out.println(JSON.toJSONString(list));

    }
}
