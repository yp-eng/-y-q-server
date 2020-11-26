package com.example.myproject.utils;



import com.example.myproject.common.CommonConst;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 系统配置文件工具类
 *
 * @author HUANG
 * @create 2017/05/03 10:05
 */
public class SysConfiguration {

    private static final Properties conf = new Properties();

    private static  final Logger logger = Logger.getLogger(SysConfiguration.class);

    private static Map<String,String> OPEN_V2_ACCOUNT_MAP = new ConcurrentHashMap<>();

    static {
        InputStream resourceAsStream = SysConfiguration.class.getClassLoader()
                  .getResourceAsStream(CommonConst.SYS_CONF_PATH);
        try {
            conf.load(resourceAsStream);
            // 加载openapi-v2-auth-user
            String openUserInfo = conf.getProperty("openapi-v2-auth-user");
            for (String userInfo : openUserInfo.split(",")) {
                String[] user = userInfo.split("@");
                OPEN_V2_ACCOUNT_MAP.put(user[0], user[1]);
            }
        } catch (IOException e) {
            logger.fatal("加载：["+ CommonConst.SYS_CONF_PATH + "]配置文件失败，系统将退出",e);
            System.exit(1);
        }finally {
            try {
                resourceAsStream.close();
            } catch (IOException e) {
                logger.fatal("加载：["+CommonConst.SYS_CONF_PATH + "]配置文件失败，系统将退出",e);
                System.exit(1);
            }
        }
    }

    private SysConfiguration(){

    }

    public static Object getProperty(String propertyKey){
        String propertyValue = conf.getProperty(propertyKey);
        return propertyValue;
    }

    public static String getOpenApiV2InfoByUsername(String username) {
        if (OPEN_V2_ACCOUNT_MAP.containsKey(username)) {
            return OPEN_V2_ACCOUNT_MAP.get(username);
        }
        return null;
    }

    public static Object getProperty(String propertyKey,Object defaultValue){
        Object propertyValue = getProperty(propertyKey);
        return propertyValue == null?defaultValue : propertyValue;
    }

    public static String  getPropertyAsString(String propertyKey){
        return (String) getProperty(propertyKey);
    }

    public static String  getPropertyAsString(String propertyKey,String defaultValue){
        return (String) getProperty(propertyKey,defaultValue);
    }

    public static Integer  getPropertyAsInteger(String propertyKey){
        String propertyValue = (String) getProperty(propertyKey);
        if(propertyValue == null){
            return null;
        }
        return Integer.parseInt(propertyValue);
    }

    public static Integer  getPropertyAsInteger(String propertyKey,Integer defaultValue){
        String propertyValue = (String) getProperty(propertyKey);
        if(propertyValue == null){
            return defaultValue;
        }
        return Integer.parseInt(propertyValue);
    }

    public static void main(String[] args) {
        String propertyAsString = SysConfiguration.getPropertyAsString("special_login_token_expire_sencods","1");
        System.out.println(propertyAsString);
    }
}
