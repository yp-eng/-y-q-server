package com.example.myproject.common;

/**
 * 响应客户端的结果集封装
 *
 * @author yp
 * @create 2020/11/23 16:57
 */
public class Result {
    private boolean success = true;
    private String msg="";
    private Object data = null;
    private String code = ResultCode.SUCCESS;

    public Result(){
        super();
    }

    public Result(boolean success, Object data) {
        this.success = success;
        this.data = data;
    }

    public Result(boolean success, String msg, Object data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

    public Result(boolean success,  String code,String msg, Object data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
        this.code = code;
    }

    public Result(boolean success, String code,String msg) {
        this.success = success;
        this.msg = msg;
        this.code = code;
    }

    public Result(String code,String msg, Object data) {
        this.msg = msg;
        this.data = data;
        this.code = code;
    }

    public Result(Object data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Result{");
        sb.append("success=").append(success);
        sb.append(", msg='").append(msg).append('\'');
        sb.append(", data=").append(data);
        sb.append(", code='").append(code).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
