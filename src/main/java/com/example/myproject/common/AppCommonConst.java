package com.example.myproject.common;

/**
 * 当前应用全局常量
 *
 * @author HUANG
 * @create 2017/05/24 10:57
 */
public interface AppCommonConst {

    /**
     * 过期时间秒数10分钟
     */
    Integer EXPIRE_TIME_SECOND = 600;

    /**
     * 访问验证信息不存在
     */
    String  ACCESS_INFO_NOT= "您没有访问权限";

    /**
     *
     */
    String TOKEN_EXPIRE = "token out time";

    /**
     * 登录成功
     */
    String LOGIN_SUCCESS = "login success";

    /**
     * 用户名或密码不匹配
     */
    String USERNAME_PASSWORD_ERROR = "用户名或密码不匹配";

    /**
     * -7天
     */
    Integer MINUS_SEVEN_DAY = -7;
    /**
     * 7天
     */
    Integer SEVEN_DAY = 7;

    /**
     * 开始时间不得早于过去一星期
     */
    String DIFFDAY_SEVEN = "开始时间不得早于过去一星期";

    /**
     * 结束时间不得早于开始时间
     */
    String EndTime_GT_StratTime = "结束时间不得早于开始时间";
    
    //秘钥常量 start
    /**
     * 公钥类型-登录
     */
    String PUBLICKEY_TYPE_LOGIN = "1";
    
    /**
     * 私钥sessionKey-登录
     */
    String PRIVATEKEY_SESSIONKEY_LOGIN = "account_login_privateKey";
    
    /**
     * 公钥类型-增加
     */
    String PUBLICKEY_TYPE_ADD = "2";
    
    /**
     * 私钥sessionKey-增加
     */
    String PRIVATEKEY_SESSIONKEY_ADD = "account_add_privateKey";
    
    /**
     * 公钥类型-更新
     */
    String PUBLICKEY_TYPE_UPDATE = "3";
    
    /**
     * 私钥sessionKey-更新
     */
    String PRIVATEKEY_SESSIONKEY_UPDATE = "account_update_privateKey";
    
    //秘钥常量 end
     
    
    
    /**
     * 验证码sessionKey
     */
    String VERIFICATIONCODE_KEY = "verificationcode";
    
    
    /**
     * 账户首次登录
     */
    Integer ACCOUNT_FIRST_LOGIN = 0 ;
    
    /**
     * 账户非首次登录
     */
    Integer ACCOUNT_NOTFIRST_LOGIN = 1 ;
    
    
    /**
     * 在线被踢下去
     */
    String WS_ACCOUNT_ONLINE_KILL = "1";
    /**
     * 没有登录
     */
    String WS_ACCOUNT_NOLOGIN = "2";
    
    /**
     * 密码被重置
     */
    String WS_ACCOUNT_PASSWORD_RESE_KILL = "3";
    
    /**
     * 账号被禁用
     */
    String WS_ACCOUNT_DISABLE_KILL= "4";
    
    /**
     * 账号被删除
     */
    String WS_ACCOUNT_DELETE_KILL= "5";
    
    // web socket responce flag start
    
    //人口管理 start
    /**
     *常住人口类型
     *
     */
    String POPULATION_TYPE_RESIDENCE="1";
    
    /**
     * 流动人口类型
     */
    String POPULATION_TYPE_FLOW="2";
    //人口管理 end
    
    
    
    /**返回值含义   0全数字          -1为空           -2失败             -3全字母     -4长度不在范围内  -5用户名称不合法
     *              -6.账户已经存在  -7.昵称已经存在  -8旧密码输入错误   -9 该角色所属级别已经存在一个超级管理员了  -10该账户对应的角色不是启用状态
     *              500成功      -500获取的秘钥为空
     * *///-5
    
    Integer RETURN_NUBER_NEGATIVE_FIVE = -5 ;
    Integer RETURN_NUBER_NEGATIVE_SIX = -6 ;
    Integer RETURN_NUBER_NEGATIVE_SEVEN = -7 ;
    Integer RETURN_NUBER_NEGATIVE_TEN = -10 ;

    /**
     * 返回消息相关
     */
    String MESSGE_KEY_ERROR= "error";//错误
    String PERMISSION = "permission";//
    String MESSGE_KEY = "message";//controller 消息提醒key

    /**
     * ES相关
     */
    String ES_PROP_NAME = "elasticsearch.properties";
    String ES_CLUSTER_NAME = "cluster_name";
    String ES_IP = "es_ips"; //es ips   hadoop1,hadoop2,hadoop3
    String ES_PORT = "es_port"; //es端口
    String ES_DOOR_INDEX = "es_door_index";
    String ES_DOOR_TYPE = "es_door_type";
    String ES_CAR_INDEX = "es_car_index";
    String ES_CAR_TYPE = "es_car_type";
    String ES_CAMERA_INDEX = "es_camera_index";
    String ES_CAMERA_TYPE = "es_camera_type";
    String ES_CAMERA_CAR_PLATFORM = "car";
    String ES_CAMERA_MULT_PLATFORM = "mult";
    String ES_CAMERA_FACE_PLATFORM = "face";

    String ES_PERSON_INDEX = "person_index";
    String ES_PERSON_TYPE = "person_type";
    String ES_CAR_PLATE_INDEX = "car_plate_index";
    String ES_CAR_PLATE_TYPE = "car_plate_type";
    String ES_TOWN_INDEX = "t_address_town_index";//区县
    String ES_TOWN_TYPE = "t_address_town_type";
    String ES_SUBDISTRICT_INDEX = "t_address_subdistrict_index";//乡镇
    String ES_SUBDISTRICT_TYPE = "t_address_subdistrict_type";
    String ES_VILLAGE_INDEX = "t_address_village_index";//村
    String ES_VILLAGE_TYPE = "t_address_village_type";
    String ES_UNIT_INDEX = "unit_index";//小区
    String ES_UNIT_TYPE = "unit_type";

    /**
     * 车行类型-进入
     */
    String IO_TYPE_IN = "0";

    /**
     * 车行类型-出去
     */
    String IO_TYPE_OUT = "1";
    
	String USER_NAME = "user_name";
	String UNIT_ID = "unit_id";
    String UNIT_ID_OTHER = "unit_id_other";
	String UNIT_NAME = "unit_name";
	String CITY_CODE = "city_code";
	String TOWN_CODE = "town_code";
	String PROVINCE_CODE = "province_code";
	String FILTER = "filter";
	String VILLAGE_CODE = "village_code";
	String TOWNSHIP_CODE = "township_code";
    String SUCCESS_MSG = "success";
}
