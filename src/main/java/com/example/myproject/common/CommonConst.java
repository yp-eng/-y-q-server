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
    
    // AccountPermission level begin
    /**
     * admin
     */
    int IS_ADMIN = 1;
    /**
     * 全国级
     */
    int ACCOUNTPERMISSION_LEVEL_SUPPER = 0;
    
    /**
     * 省级
     */
    int ACCOUNTPERMISSION_LEVEL_PROVICE = 1;
    
    /**
     * 城市级
     */
    int ACCOUNTPERMISSION_LEVEL_CITY = 2;
    
    /**
     * 区县级
     */
    int ACCOUNTPERMISSION_LEVEL_TOWN = 3;
    
    /**
     * 乡镇级
     */
    int ACCOUNTPERMISSION_LEVEL_TOWNSHIP = 4;
    
    /**
     * 村、社区级
     */
    int ACCOUNTPERMISSION_LEVEL_VILLAGE = 5;
    
    /**
     *小区级 
     */
    int ACCOUNTPERMISSION_LEVEL_UNIT = 6;

    /**
     *派出所级
     */
    int ACCOUNTPERMISSION_LEVEL_POLICE = 7;
    //AccountPermission level end
    
    //角色类型种类 start
    
    /**
     * 角色种类：超级管理员
     */
    int ROLE_VARIETY_ADMIN = 0;
    
    /**
     * 角色种类：普通管理员
     */
    int ROLE_VARIETY_ADMIN_ = 1;
    
    /**
     * 角色种类：普通用户类型
     */
    int ROLE_VARIETY_PUTONG = 2;
    
    //角色类型种类 end
    
    //账户状态 0：删除  1：有效 2：禁用' start
    /**
     * 账号状态：删除
     */
    Integer ACCOUNT_STATUS_DELETE = 0;
    
    /**
     * 账号状态：有效
     */
    Integer ACCOUNT_STATUS_VALID = 1;
    
    /**
     * 账号状态：禁用
     */
    Integer ACCOUNT_STATUS_DISABLED = 2;
    //账户状态 0：删除  1：有效 2：禁用' end
    
    //sys conf start
    /**
     * 搜索项目context Path
     */
    String SYS_PATH_BIGDATA_SEARCH = SysConfiguration.getPropertyAsString("sys-path-bigdata-search");
    
    /**
     * 安防项目context Path
     */
    String SYS_PATH_SECURITYCENTER = SysConfiguration.getPropertyAsString("sys-path-securitycenter");
    //sys conf end

    /**
     * 安防项目context Path
     */
    String SYSTEM_NAME = SysConfiguration.getPropertyAsString("sys-name");
    
    /**
     * websocket 监听地址(全路劲)
     */
    String SYS_BASE_PATH_WS = SysConfiguration.getPropertyAsString("sys-base-path-ws", "");
    //sys conf end

    int LIVING_STATE_UNKNOW = 0;//未知
    int LIVING_STATE_CONSISTENT = 1;//人户一致
    int LIVING_STATE_SEPARATE = 2;//人在户不在
    int LIVING_STATE_FLOWING = 3;//流动人口

    int HOUSE_USE_STATUS_ZZ = 1;//自住
    int HOUSE_USE_STATUS_FZ = 2;//放租
    int HOUSE_USE_STATUS_SY = 3;//商用
    int HOUSE_USE_STATUS_KZ = 4;//空置
    int HOUSE_USE_STATUS_QT = 5;//其他
    int HOUSE_USE_STATUS_DCF = 6;//待查房

}
