package com.example.myproject.websocket;

import com.example.myproject.common.AppCommonConst;
import com.example.myproject.exception.ParameterException;
import com.example.myproject.utils.JedisUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.Jedis;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;
import java.io.IOException;
import java.util.List;

@ServerEndpoint(value = "/websocket")
public class WebSocketEndpoint extends ServerEndpointConfig.Configurator {
    
    private static Log log = LogFactory.getLog(WebSocketEndpoint.class);
    /**
     * 回话session
     */
    private Session session;
    
    /**
     * 登录token
     */
    private String token;
    
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public Session getSession() {
        return session;
    }
    
    public void setSession(Session session) {
        this.session = session;
        //设置异步发送超时时间
        this.session.getAsyncRemote().setSendTimeout(1000);
        
    }
    
    @OnMessage
    public void onMessage(String message, Session session) throws IOException, InterruptedException {
    }
    
    @OnOpen
    public void onOpen(Session session) throws IOException {
        this.session = session;
//        HttpSession httpSession = (HttpSession) session.getUserProperties().get(HttpSession.class.getName());
        List<String> tokenList = session.getRequestParameterMap().get("token");
        
        String _token = null;
        //获取参数token
        if (tokenList != null && tokenList.size() > 0) {
            _token = tokenList.get(0);
        }
        
        this.setToken(_token);
        
        if(this.getToken() != null){
            try {
                verifyToken(getToken());
                WebSocketPool.getInstance().addConn(getToken(), this);
                log.info("WebSocketTest class onOpen method Client connected token=" + this.token);
            }catch (ParameterException ex){
                log.debug(ex.getMessage(),ex);
                //发送当前没有登录的标识符
                sendMessage(AppCommonConst.WS_ACCOUNT_NOLOGIN);
                //close
                session.close();
            }catch (Exception ex){
                log.debug(ex.getMessage(),ex);
                //close
                session.close();
            }
        }
    }
    
    public void verifyToken(String token){
        Jedis jedis = null;
        try {
            jedis = JedisUtil.getJedis();
            Boolean exists = jedis.exists(token);
            if(!exists) {
                throw new ParameterException("校验token失败");
            }
        }catch (Exception ex){
            throw ex;
        }finally {
            if(jedis != null)
                jedis.close();
        }
        
    }
    
    @OnClose
    public void close(Session session) {
        log.info("WebSocketTest class close method token=" + token);
        if (getToken() != null) {
            WebSocketPool.getInstance().deleteConn(token);
        }
    }
    
    @OnError
    public void error(Throwable throwable) {
        log.info("WebSocketTest class error method 异常=" + throwable.getMessage());
        if (getToken() != null) {
            WebSocketPool.getInstance().deleteConn(token);
        }
    }
    
    public void sendMessage(String message) throws IOException {
        if(this.session.isOpen()) {
            this.session.getAsyncRemote().sendText(message);
        }
    }
    
    
    public static void main(String[] args) {
        String token = "TOKEN:21232f297a57a5a743894a0e4a801fc3:7eb326e4-e66e-45f4-b9ef-c7dc90a40ec4";
        if (token == null || token.lastIndexOf(":") < 0) {
            throw new RuntimeException("token params invalid");
        }
        token = token.substring(0, token.lastIndexOf(":"));
        System.out.println(token);
    }
    
}