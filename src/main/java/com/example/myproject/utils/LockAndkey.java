package com.example.myproject.utils;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 加密和解密
 * 2017 Oeasy Inc. All Rights Reserved
 *
 * @author xiehui
 * @date 2017/8/28
 */
@SuppressWarnings("all")
public class LockAndkey {

    protected final static Logger logger = LoggerFactory.getLogger(LockAndkey.class);
    static String skey = "01123456";
    //true or false
    private static final String isInt = null; //SysConfiguration.getPropertyAsString("deploy-isInt");
    private static final String USER_PRE_URL =  null; //SysConfiguration.getPropertyAsString("deploy-isInt-user-preurl");
    private static final String USER_END_URL =  null; //SysConfiguration.getPropertyAsString("deploy-isInt-user-endurl");
    private static final String PRE_URL =  null; //SysConfiguration.getPropertyAsString("deploy-isInt-preurl");
    private static final String END_URL =  null; //SysConfiguration.getPropertyAsString("deploy-isInt-endurl");
    private static final String PRE_URL_LOCAL = null; //SysConfiguration.getPropertyAsString("deploy-isInt-preurl-local");
    private static final String VIDEO_PRE_URL =  null; //SysConfiguration.getPropertyAsString("deploy-isInt-preurl");
    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy");
    private static final String curYear = df.format(new Date());

//    public static void encryptParameterEntity(ParameterEntity src) {
//        if (src == null) {
//            return;
//        }
//        if (src.getId_code() != null && src.getId_code().length() > 0) {
//            src.setId_code(encrypt(src.getId_code()));
//        }
//        if (src.getTelephone() != null && src.getTelephone().length() > 0) {
//            src.setTelephone(encrypt(src.getTelephone()));
//        }
//    }

    /**
     * @param isHide 是否需要隐藏
     * @return
     */
    public static void decodeListMap(List<Map<String, Object>> mapList, boolean isHide) {
        if (mapList == null || mapList.size() == 0) {
            return;
        }
        for (Map map : mapList) {
            decodeMap(map, isHide);
        }
    }

    public static void decodeMap(Map map, boolean isHide) {
        if (map == null) {
            return;
        }
        //根据身份证算年龄
        Object idCode = map.get("id_code") == null ? map.get("idCode") : map.get("id_code");
        map.put("id_code", "");
        if (idCode != null && idCode.toString().length() >= 16) {
            try {
                String code = idCode.toString();
                if (code.length() != 18) { //解密
                    try {
                        code = decode(code);
                    } catch (Exception e) {
                        code = code;
                    }
                }
                if (code.length() == 18) { //正常的身份证长度
                    String dates = code.substring(6, 10);
                    int age = Integer.parseInt(curYear) - Integer.parseInt(dates);
                    map.put("age", age);
                }
                if (isHide) {
                    code = hideCode(code);
                }
                if (map.containsKey("id_code")) {
                    map.put("id_code", code);
                }
                if (map.containsKey("idCode")) {
                    map.put("idCode", code);
                }
            } catch (Exception e) {
                logger.error("数据组装错误", JSON.toJSONString(e));
            }
        }
        Object telephone = map.get("telephone");
        if (telephone != null && telephone.toString().length() >= 11) {
            String phone = telephone.toString();
            if (phone.length() > 11) {
                try {
                    phone = decode(phone); //解密
                } catch (Exception e) {
                    phone = phone;
                }
            }
            if (isHide) {
                phone = hideCode(phone);
            }
            map.put("telephone", phone);
        }
        if (map.get("user_image") != null && ((String) map.get("user_image")).length() > 0) {
            if (isInt.equals("false")) {
                String image = "" + map.get("user_image");
                if (image.startsWith("http")) {
                    String[] imgs = image.split("/");
                    image = imgs[imgs.length - 1];
                    if (image.endsWith(".png")) {
                        image = image.replace(".png", "");
                    } else if (image.endsWith("?p=0")) {
                        image = image.replace("?p=0", "");
                    }
                    image = USER_PRE_URL + image + USER_END_URL;
                    map.put("user_image", image);
                } else if (image.length() > 0) {
                    image = USER_PRE_URL + image + USER_END_URL;
                    map.put("user_image", image);
                }
            }
        }
        if (isInt.equals("false") && map.get("videourl") != null && map.get("videourl").toString().length() > 35) {
            String videourl = map.get("videourl").toString();
            if (videourl.startsWith("ftp")) {
                String[] pathArr = videourl.split("/");
                String videoUrlTemp = "";
                for (int i = 3; i < pathArr.length; i++) {
                    videoUrlTemp += "/" + pathArr[i];
                }
                map.put("videourl", VIDEO_PRE_URL + videoUrlTemp);
            } else if (!videourl.startsWith("http")) {
                videourl = VIDEO_PRE_URL + "/" + videourl;
                map.put("videourl", videourl);
            }
        }
    }

    public static void decodeMap(Map map, String imageName) {
        if (map.get(imageName) != null && ((String) map.get(imageName)).length() > 0) {
            if (isInt.equals("false")) {
                String image = "" + map.get(imageName);
                if (image.startsWith("http")) {
                    String[] imgs = image.split("/");
                    image = imgs[imgs.length - 1];
                    if (image.endsWith(".png")) {
                        image = image.replace(".png", "");
                    } else if (image.endsWith("?p=0")) {
                        image = image.replace("?p=0", "");
                    }
                    image = USER_PRE_URL + image + USER_END_URL;
                    map.put(imageName, image);
                } else if (image.length() > 0) {
                    image = USER_PRE_URL + image + USER_END_URL;
                    map.put(imageName, image);
                }
            }
        }
    }

    public static void decodeMapLocal(Map map, String imageName) {
        if (map.get(imageName) != null && ((String) map.get(imageName)).length() > 0) {
            if (isInt.equals("false")) {
                String image = "" + map.get(imageName);
                if (image.startsWith("http")) {
                    String[] imgs = image.split("/");
                    image = imgs[imgs.length - 1];
                    if (image.endsWith(".png")) {
                        image = image.replace(".png", "");
                    } else if (image.endsWith("?p=0")) {
                        image = image.replace("?p=0", "");
                    }
                    image = PRE_URL_LOCAL + image + END_URL;
                    map.put(imageName, image);
                } else if (image.length() > 0) {
                    image = PRE_URL_LOCAL + image + END_URL;
                    map.put(imageName, image);
                }
            }
        }
    }

    //隐藏身份证中间位和手机中间4位   1234************89   158****1234
    public static String hideCode(String src) {
        String returnCode = src;
        if (src != null && src.length() == 18) {
            //身份证
            returnCode = src.substring(0, 4) + "************" + src.substring(16, 18);
        } else if (src != null && src.length() == 11) {
            returnCode = src.substring(0, 3) + "****" + src.substring(7, 11);
        }
        return returnCode;
    }

    //加密
    public static String encrypt(String src) {
        if (src == null || src.length() == 0) {
            return src;
        } else {
            return byteArr2HexStr(DESUtil.encrypt(src.getBytes(), skey.getBytes()));

        }
    }

    //解密
    public static String decode(String src) {
        if (src == null || src.length() == 0) {
            return src;
        } else if (!regex16(src)) {
            return null;
        } else {
            return new String(DESUtil.decode(hexStr2ByteArr(src), skey.getBytes()));
        }
    }

    /**
     * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]
     * hexStr2ByteArr(String strIn) 互为可逆的转换过程
     *
     * @param arrB 需要转换的byte数组
     * @return 转换后的字符串
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出
     */
    public static String byteArr2HexStr(byte[] arrB) {
        int iLen = arrB.length;
        // 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍..一个byte转成16进制最多不会超过两位。FF
        StringBuffer sb = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            // 把负数转换为正数
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            // 小于0F的数需要在前面补0
            if (intTmp < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(intTmp, 16)); // 16代表进制
        }
        return sb.toString();
    }

    /**
     * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)
     * 互为可逆的转换过程
     *
     * @param strIn 需要转换的字符串
     * @return 转换后的byte数组
     * @throws Exception 本方法不处理任何异常，所有异常全部抛出
     * @author <a href="mailto:leo841001@163.com">LiGuoQing</a>
     */
    public static byte[] hexStr2ByteArr(String strIn) {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;

        // 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    public static boolean regex16(String str) {
        String regex = "^[A-Fa-f0-9]+$";
        if (str.length() % 2 == 0 && str.matches(regex)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param picName 图片字段名称
     * @param mapList 数据集合
     * @return
     */
    public static void decodeListMap(List<Map> mapList, String picName, boolean isHide) {
        if (mapList == null || mapList.size() == 0) {
            return;
        }
        for (Map map : mapList) {
            if (isInt.equals("false") && map.get("videourl") != null && map.get("videourl").toString().length() > 35) {
                String videourl = map.get("videourl").toString();
                if (videourl.startsWith("ftp")) {
                    String[] pathArr = videourl.split("/");
                    String videoUrlTemp = "";
                    for (int i = 3; i < pathArr.length; i++) {
                        videoUrlTemp += "/" + pathArr[i];
                    }
                    map.put("videourl", VIDEO_PRE_URL + videoUrlTemp);
                } else if (!videourl.startsWith("http")) {
                    videourl = VIDEO_PRE_URL + "/" + videourl;
                    map.put("videourl", videourl);
                }
            }
            if (map.get(picName) != null && (map.get(picName) instanceof String) && ((String) map.get(picName)).length() > 0) {
                if (isInt.equals("false")) {
                    String image = "" + map.get(picName);
                    if (image.startsWith("http")) {
                        String[] imgs = image.split("/");
                        image = imgs[imgs.length - 1];
                        if (image.endsWith(".png")) {
                            image = image.replace(".png", "");
                        } else if (image.endsWith("?p=0")) {
                            image = image.replace("?p=0", "");
                        }
                        image = PRE_URL + image + END_URL;
                        map.put(picName, image);
                    } else if (image.length() > 0) {
                        if (image.startsWith("/server")) {
                            String[] pics = image.split(",");
                            String newPicUrl = "";
                            for (String pic : pics) {
                                newPicUrl += PRE_URL + pic + END_URL + ",";
                            }
                            map.put(picName, newPicUrl.substring(0, newPicUrl.length() - 1));
                        } else {
                            String[] pics = image.split(",");
                            String newPicUrl = "";
                            for (String pic : pics) {
                                newPicUrl += PRE_URL_LOCAL + pic + ",";
                            }
                            if (newPicUrl.length() > 0) {
                                map.put(picName, newPicUrl.substring(0, newPicUrl.length() - 1));
                            }
                        }
                    }
                }
                if (map.get(picName).toString().length() < 5) { //去除 picurl:",," 的情况
                    map.put(picName, "");
                }
            }
            Object idCode = map.get("id_code");
            if (idCode != null && idCode.toString().length() >= 16) {
                String code = idCode.toString();
                if (isHide) {
                    code = hideCode(code);
                }
                map.put("id_code", code);
            }
            Object telephone = map.get("telephone");
            if (telephone != null && telephone.toString().length() >= 11) {
                String phone = telephone.toString();
                if (isHide) {
                    phone = hideCode(phone);
                }
                map.put("telephone", phone);
            }
        }
    }


    public static void main(String[] args) {
        String string = "18953053222  ";
        System.out.println(encrypt(string));
        System.out.println(decode("e6c74e6687281c27b1e9891d742ae37d"));
        /*Map map=new HashMap();
        map.put("id_code","91eb5f9d6100e0ed8a84cb126931874828a73f19c81eaa53");
        map.put("telephone","54cb069f32a08d4b62b257e1b31aa43e");
        map.put("user_image","http://218.18.160.183:6300/48d2752fda7758b046da5b41052e54d4?p=0");
        decodeMap(map,true);
        System.out.println(map.toString());*/
    }


}
