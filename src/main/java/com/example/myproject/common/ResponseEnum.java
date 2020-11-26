package com.example.myproject.common;
/**
 * @Date 2020年11月23日
 * @author dnc
 * @Description 系统统一消息码值枚举
 */
public enum ResponseEnum {
	SUCCESS("200","OK"),
	ERROR("500","系统内部异常"),
	NOT_FOUND("404","未能识别"),
	
	/**参数相关**/
	PARAM_ERR("4000","参数错误"),
	PARAM_EMPT("4001","参数为空"),
	USER_UNKNOW("4002","用户不存在"),
	USER_PASS_UNMATCH("4003","用户名或密码错误"),
	CAPTCHA_UNMATCH("4004","验证码错误"),
	FILESIZE_LIMIT_EXCEEDED("4005","文件过大"),
	
	/**操作相关**/
	AUTH_ERR("5000","无权操作"),
	FAILED("5001","操作失败"),
	DATA_AUTH_ERR("5002","用户暂无小区权限"),
	
	/**结果数据**/
	DATA_UNEXIST("6000","数据不存在"),
	DATA_EXISTED("6001","数据已存在"),
	
	;
	private String code;
	private String message;
	
	ResponseEnum(String code,String message){
		this.code = code;
		this.message=message;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
