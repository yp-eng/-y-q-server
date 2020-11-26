package com.example.myproject.exception;

/**
 * 参数异常
 *
 * @author HUANG
 * @create 2017/04/25 11:58
 */
public class ParameterException extends RuntimeException {
    
    String fieldName;
    
    Object fieldValue;
    
    public ParameterException() {
    }
    
    public ParameterException(String message,String fieldName, Object fieldValue)
    {
        super(message);
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
    
    public ParameterException(String fieldName, Object fieldValue)
    {
        super("fileName:["+fieldName+"] fieldValue:["+fieldValue+"]");
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
    
    public ParameterException(String message) {
        super(message);
    }
    
    public ParameterException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ParameterException(Throwable cause) {
        super(cause);
    }
    
    public ParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
    public Object getFieldValue() {
        return fieldValue;
    }
    
    public void setFieldValue(Object fieldValue) {
        this.fieldValue = fieldValue;
    }
    
    public String getFieldName() {
        return fieldName;
    }
    
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
    
    @Override
    public String toString() {
        return super.toString();
    }
    
    public static void main(String[] args) {
        System.out.println( new ParameterException("role_name","asdas"));
    }
}
