package com.example.myproject.common;

/**
 * 响应客户端code码
 *
 * @author yp
 * @create 2020/11/23 16:57
 */
public interface ResultCode {

    /**
     * 操作成功返回200
     */
    String SUCCESS_CODE_TWO_HUNDRED = "200";

    /**
     * 操作成功
     */
    String SUCCESS="0000";
    /**
     * 系统异常
     */
    String ERROR="0001";//
    /**
     * 操作失败
     */
    String FAILURE="0002";//
    /**
     * 没有权限操作
     */
    String NO_PERMISSION="0003";//
    /**
     * 参数异常
     */
    String ILLEGALARGUMENT = "0004"; //
    
    /**
     * 首次登录
     */
    String FIRST_LOGIN = "0005"; //
    
    /**
     * 当前没有登录
     */
    String  NO_LOGIN="0006";
    
    /**
     * 当前没有数据
     */
    String  NO_DATA="0007";
}
