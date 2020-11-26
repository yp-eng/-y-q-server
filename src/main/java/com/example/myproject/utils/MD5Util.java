package com.example.myproject.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**MD5 加密算法
 * @author HUANG
 * @create 2017/04/14 15:36
 */
public class MD5Util {
    
    /**
     * md5加密方法
     * @param password
     * @return
     */
    public static String md5(String password) {
        
        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes());
            StringBuffer buffer = new StringBuffer();
            // 把没一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }
            
            // 标准的md5加密后的结果
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
    
    public static void main(String[] args) {
        String huang = MD5Util.md5("cba000000");
        System.out.println(huang);
    }
    
}
