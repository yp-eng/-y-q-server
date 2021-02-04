package com.example.myproject.controller;

import com.example.myproject.common.CommonUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

import static com.example.myproject.common.AppCommonConst.VERIFICATIONCODE_KEY;


/**
 * @author: yp
 * @time: 2020/12/8 14:40
 * @description:
 */
@RestController
@RequestMapping("/image/code")
public class ImageCodeController {

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @RequestMapping(value = {"/"})
    public String index() {
        return "/index";
    }
    /**
     * 登录验证码图片
     */
    @GetMapping(value = {"/loginValidateCode"})
    public void loginValidateCode(HttpServletRequest request, HttpServletResponse response) throws Exception{
        CommonUtil.validateCode(request,response,defaultKaptcha,VERIFICATIONCODE_KEY);
    }

    /**
     * 检查验证码是否正确
     */
    @PostMapping("/checkLoginValidateCode")
    public HashMap checkLoginValidateCode(HttpServletRequest request, @RequestParam("validateCode")String validateCode) {
        String loginValidateCode = request.getSession().getAttribute(VERIFICATIONCODE_KEY).toString();
        HashMap<String,Object> map = new HashMap<String,Object>();
        if(loginValidateCode == null){
            map.put("status",null);//验证码过期
        }else if(loginValidateCode.equals(validateCode)){
            map.put("status",true);//验证码正确
        }else if(!loginValidateCode.equals(validateCode)){
            map.put("status",false);//验证码不正确
        }
        map.put("code",200);
        return map;
    }

}
