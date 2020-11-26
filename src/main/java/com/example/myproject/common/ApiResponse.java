package com.example.myproject.common;

/**
 * @Date 2018年3月25日
 * @author dnc
 * @Description 统一接口返回
 */
public class ApiResponse<T> {
	
	private String code;
	private String desc;
	private T data;
	
	public ApiResponse(){}
	
	public ApiResponse(String code, String desc, T data) {
		this.code = code;
		this.desc = desc;
		this.data = data;
	}
	
	public static ApiResponse<String> success() {
		return new ApiResponse<String>(ResponseEnum.SUCCESS.getCode(),ResponseEnum.SUCCESS.getMessage(),null);
	}
	
	public static <T> ApiResponse<T> success(T result) {
		return new ApiResponse<T>(ResponseEnum.SUCCESS.getCode(),ResponseEnum.SUCCESS.getMessage(),result);
	}
	
	public static ApiResponse<String> error() {
		return new ApiResponse<String>(ResponseEnum.ERROR.getCode(),ResponseEnum.ERROR.getMessage(),null);
	}
	
	public static ApiResponse<String> error(String desc) {
		return new ApiResponse<String>(ResponseEnum.ERROR.getCode(),desc,null);
	}
	
	public static ApiResponse<String> error(String code, String desc) {
		return new ApiResponse<String>(code,desc,null);
	}
	
	public static <T> ApiResponse<T> error(ResponseEnum resp) {
		return new ApiResponse<T>(resp.getCode(),resp.getMessage(),null);
	}
	
	public static <T> ApiResponse<T> response(ResponseEnum resp) {
		return new ApiResponse<T>(resp.getCode(),resp.getMessage(),null);
	}
	
	public static <T> ApiResponse<T> response(String code, String desc) {
		return new ApiResponse<T>(code,desc,null);
	}
	
	public static <T> ApiResponse<T> response(String code, String desc,T data) {
		return new ApiResponse<T>(code,desc,data);
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

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
