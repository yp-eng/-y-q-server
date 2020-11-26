package com.example.myproject.common;
/**
 * @Date 2018年3月25日
 * @author dnc
 * @Description 统一异常
 */
public class ApiException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	private String code;
	private String desc;
	
	public ApiException(){
		this.code=ResponseEnum.ERROR.getCode();
		this.desc = ResponseEnum.ERROR.getMessage();
	}
	
	public ApiException(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	public ApiException(String desc) {
		this(ResponseEnum.ERROR.getCode(),desc);
	}
	
	public ApiException(ResponseEnum resp) {
		this(resp.getCode(),resp.getMessage());
	}
	
	public static ApiException err(String code, String desc) {
		return new ApiException(code, desc);
	}
	
	public static ApiException err(String code) {
		return new ApiException(code, ResponseEnum.ERROR.getMessage());
	}
	
	public static ApiException err(ResponseEnum resp) {
		return new ApiException(resp);
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
