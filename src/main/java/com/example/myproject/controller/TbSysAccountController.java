package com.example.myproject.controller;


import com.example.myproject.common.AppCommonConst;
import com.example.myproject.common.CommonConst;
import com.example.myproject.common.Result;
import com.example.myproject.common.ResultCode;
import com.example.myproject.entity.TbSysAccount;
import com.example.myproject.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.interfaces.RSAPrivateKey;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yp
 * @since 2020-11-24
 */
@RestController
@RequestMapping("/tbSysAccount")
public class TbSysAccountController extends BaseController{

    @RequestMapping(value = "/")
    public String ceshi(){
        return "/static/login.html";
    }


    @RequestMapping(value = "/login")
    public Object login(TbSysAccount account, @NotBlank String verificationcode, HttpSession session, HttpServletResponse response, HttpServletRequest request) throws Exception  {
        try {
            System.out.println("fetchRSAPublicKey sessionId = "+session.getId());
            session = reGenerateSessionId(session, request);//重置SessionId

            String session_verificationcode = (String) session.getAttribute(AppCommonConst.VERIFICATIONCODE_KEY);
            if (session_verificationcode != null && !session_verificationcode.toLowerCase().equals(verificationcode.toLowerCase())) {
                return returnFail("验证码错误", "3");
            }
            if (StringUtils.isBlank(account.getPassword()) || StringUtils.isBlank(account.getAccount())) {
                return returnFail("用户名或密码不匹配", "-1");
            }
            session.removeAttribute(AppCommonConst.VERIFICATIONCODE_KEY);//验证成功删除验证码
            if(true){
                
            }
            RSAPrivateKey privateKey = (RSAPrivateKey) session.getAttribute(AppCommonConst.PRIVATEKEY_SESSIONKEY_LOGIN);
            if (privateKey == null) {
                return returnFail("获取密钥失败", "-1");
            }

            Jedis jedis = JedisUtil.getJedis(); //redis操作对象
            String loginErrorCoutKey = account.getAccount() + DateUtil.getCurrentDay() + "_loginErrorCount";
            String loginErrorTimeKey = account.getAccount() + DateUtil.getCurrentDay() + "_loginErrorTime";
            //加上同步锁，防止同一个秘钥两次登录的情况
            synchronized (privateKey) {
                session.removeAttribute(AppCommonConst.PRIVATEKEY_SESSIONKEY_LOGIN);//删除session中的私钥
                //密码错误10次锁定5分钟，判断上次被锁的时间
                String loginErrorTime = jedis.get(loginErrorTimeKey);
                if (StringUtils.isNotBlank(loginErrorTime)) {
                    return returnFail("您的账号已被锁定5分钟，请稍后登录", "-1");
                }
                String decryptPassword = RSAUtils.decryptByPrivateKey(account.getPassword(), privateKey);
                // 前台解密后的密码为倒叙的
                // decryptPassword = new StringBuffer(decryptPassword).reverse().toString();
                account.setPassword(MD5Util.md5(decryptPassword));
                TbSysAccount loginAccount =  null ;//accountService.login(account, request);
                if (loginAccount == null) {
                    //判断一天内登录错误次数
                    String loginErrorCount = jedis.get(loginErrorCoutKey);
                    int errorCount = 0;
                    if (StringUtils.isNotBlank(loginErrorCount)) {
                        errorCount = Integer.parseInt(loginErrorCount);
                    }
                    errorCount = errorCount + 1;
                    if (errorCount >= 10) {
                        errorCount = 0; //锁定后错误次数重新置0
                        jedis.setex(loginErrorCoutKey, 60 * 60 * 24, errorCount + "");
                        jedis.setex(loginErrorTimeKey, 60 * 5, DateUtil.getCurrentDate());
                        return returnFail("密码错误10次锁定5分钟", "-1");
                    }
                    jedis.setex(loginErrorCoutKey, 60 * 60 * 24, errorCount + "");
                    if (errorCount >= 7) {
                        return returnFail("用户名或密码不匹配，您还有" + (10 - errorCount) + "次机会", "-1");
                    }
                    return returnFail("用户名或密码不匹配", "4");
                }
               // if (loginAccount.isDelete()) return returnFail("该账号已删除", "1");
                //if (loginAccount.isDisable()) return returnFail("该账号已被禁用", "1");

                //把当前登录对象设置到session中
                session.setAttribute(CommonConst.USER_SESSION_KEY, loginAccount);
                try {
                    String token =  null ;//AccountUtil.verifyLogined(request, jedis);
                    //判断当前cookie token有没有登录过，如果携带过来的token已经登录了证明是自己重新登录的，不需要推送退出的消息
                    if (token == null) {
                        //是已经登录了，且不是自己重新登录
                        //如果当前登录的账号有人在登录了,就发送一条踢下线的退出提示消息
                        SysWebSocketUtil.sendMessagedKill(loginAccount, AppCommonConst.WS_ACCOUNT_ONLINE_KILL, jedis);
                    }
                    //注册到redis 共享账号信息
//                    AccountUtil.registerAccountToRedis(loginAccount, jedis, response);
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    if (jedis != null) jedis.close();
                }
                try {
                    // 如果访问量大了 ，可以做一个ip与地址的本地存储库，不用每次去请求外部链接获取
//                    String ip = IpAddressUtil.getIp(request);
//                    SysLogModel sysLogModel = OperateUtil.setLogBean(request, loginAccount, OperateModule.ID_42.getId(), "登录系统", true);
//                    operateLogService.insertOperateLog(sysLogModel);
//                    //新增一条登陆历史记录
//                    accountService.insertLoginHistory(new LoginHistory(loginAccount.getId(), "", ip, new Date()));
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("login.insertOperateLog error:" + e.getMessage());
                }
                boolean isFirst = false;//loginAccount.isFirst();
                if (isFirst) {
                    return new Result(true, ResultCode.FIRST_LOGIN, "首次登录", loginAccount);
                } else {
                    return returnSuccess(loginAccount); //登录成功
                }
            }



        }catch (Exception e){
            e.printStackTrace();
        }

        return returnFail("登录失败", "2");
    }

    /**
     * 重置sessionid，原session中的数据自动转存到新session中
     *
     * @param session
     * @param request
     * @return
     */
    private HttpSession reGenerateSessionId(HttpSession session, HttpServletRequest request) {
        //首先将原session中的数据转移至一临时map中
        Map<String, Object> tempMap = new HashMap();
        Enumeration<String> sessionNames = session.getAttributeNames();
        while (sessionNames.hasMoreElements()) {
            String sessionName = sessionNames.nextElement();
            tempMap.put(sessionName, session.getAttribute(sessionName));
        }
        //注销原session，为的是重置sessionId
        session.invalidate();
        //将临时map中的数据转移至新session
        session = request.getSession();
        for (Map.Entry<String, Object> entry : tempMap.entrySet()) {
            session.setAttribute(entry.getKey(), entry.getValue());
        }
        return session;
    }

}
