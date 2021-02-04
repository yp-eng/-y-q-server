package com.example.myproject.common;


import com.example.myproject.utils.SysConfiguration;

/**
 * 公共常量
 *
 * @author HUANG
 * @create 2017/01/10 19:55
 */
public interface CommonConst {

    String EMPTY_STRING = "";

    /**
     * 超级用户
     */
    String SUPER_USER = "000000";
    
    /**
     * 登录账号 sessionKey
     */
    String USER_SESSION_KEY = "$userModle";
    
    /**
     * redis配置文件名称
     */
    String REDIS_PROP_NAME = "redis-config.properties";
    
    /**
     * redis config key host
     */
    String HOST = "host";
    
    /**
     * redis config key port
     */
    String PORT = "port";
    
    /**
     * redis config key password
     */
    String PASSWORD = "password";
    
    /**
     * redis config key dbindex
     */
    String DBINDEX = "dbindex";
    
    
    /**
     * 登录session redis过期时间 60*60*24*15
     */
    Integer LOGIN_TOKEN_EXPIRE_SENCODS = SysConfiguration.getPropertyAsInteger("login_token_expire_sencods", 60 *10); //登录token redis10分钟
    
    /**
     * 特殊账户 登录session redis过期时间
     */
    Integer SPECIAL_LOGIN_TOKEN_EXPIRE_SENCODS = SysConfiguration.getPropertyAsInteger("special_login_token_expire_sencods", 60 *10); //登录token redis 10分钟
    
    /**
     * 登录cookie-name
     */
    String LOGIN_TOKEN_COOKIE_NAME = "PORTAL_TOKEN";

    /**
     * 登录cookie-name
     */
    String LOGIN_TOKEN_COOKIE_DOMAIN = SysConfiguration.getPropertyAsString("login_token_cookie_domain", "");
    
    /**
     * sys config classpath:filename
     */
    String SYS_CONF_PATH = "sys-conf.properties";



    String ACCESS_CONTROL= SysConfiguration.getPropertyAsString("access_control", "true");


}
