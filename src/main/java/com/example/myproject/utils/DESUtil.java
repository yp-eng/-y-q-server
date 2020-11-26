package com.example.myproject.utils;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

/**
 *
 * 类  描  述：DES加密解密算法
 */
public final class DESUtil {

    //加密算是是des
    private static final String ALGORITHM = "DES";
    //转换格式
    private static final String TRANSFORMATION = "DES/ECB/PKCS5Padding";

    //利用8个字节64位的key给src加密
    @SuppressWarnings("unused")
    public static byte[] encrypt(byte[] src,byte[]key)
    {
        try {
            //加密
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            KeySpec keySpec = new DESKeySpec(key);
            SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey,new SecureRandom());
            byte[] enMsgBytes = cipher.doFinal(src);
            return enMsgBytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //利用8个字节64位的key给src解密
    @SuppressWarnings("unused")
    public static byte[] decode(byte[] encryptBytes,byte[]key){
        try {
            //解密
            //Cipher deCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            Cipher deCipher = Cipher.getInstance(TRANSFORMATION);
            SecretKeyFactory deDecretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            KeySpec deKeySpec = new DESKeySpec(key);
            SecretKey deSecretKey = deDecretKeyFactory.generateSecret(deKeySpec);
            deCipher.init(Cipher.DECRYPT_MODE, deSecretKey,new SecureRandom());
            byte[] deMsgBytes = deCipher.doFinal(encryptBytes);
            return deMsgBytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String key = "12345678";

    public static void main(String[] args) throws Exception{
        String msg = "hello world. 你好，DES";
        System.out.println("加密前：" + msg);
        byte[] encryptBytes = DESUtil.encrypt(msg.getBytes(),key.getBytes());
        System.out.println("加密后："+new String(encryptBytes));
        byte[] deMsgBytes = DESUtil.decode(encryptBytes,key.getBytes());
        System.out.println("解密后："+new String(deMsgBytes));
    }
}