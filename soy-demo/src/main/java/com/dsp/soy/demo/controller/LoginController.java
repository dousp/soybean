package com.dsp.soy.demo.controller;

import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.utils.CaptchaUtil;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author dsp
 * @date 2020-09-26
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Resource
    RedissonClient redissonClient;

    @GetMapping("")
    public String login(HttpServletRequest request, String username, String password, String verCode){

        // 获取redis中的验证码
        String key = (String) redissonClient.getBucket(request.getHeader("key")).get();
        // 判断验证码
        if (verCode==null || !key.equals(verCode.trim().toLowerCase())) {
            return "验证码不正确";
        }else {
            return "验证通过";
        }
        // if (!CaptchaUtil.ver(verCode, request)) {
        //     CaptchaUtil.clear(request);  // 清除session中的验证码
        //     return "验证码不正确";
        // }else {
        //     return "验证通过";
        // }
    }

    @GetMapping(value = "/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(140, 50, 2);
        String verCode = captcha.text().toLowerCase();
        String key = UUID.randomUUID().toString();
        // 存入redis并设置过期时间为30分钟
        RBucket<String> rBucket = redissonClient.getBucket(key);
        rBucket.set(verCode,30,TimeUnit.MINUTES);
        // 将key和base64返回给前端
        response.setHeader("key",key);

        System.out.println(rBucket.get());
        System.out.println(captcha.text());
        System.out.println(key);

        CaptchaUtil.out(captcha, request, response);
    }

}
