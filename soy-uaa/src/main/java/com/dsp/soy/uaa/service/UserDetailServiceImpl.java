package com.dsp.soy.uaa.service;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author dsp
 * @date 2020-10-04
 */
@Component
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username.equals("dd")){
            com.dsp.soy.uaa.model.User user = new com.dsp.soy.uaa.model.User();
            user.setFullname("dsp");
            user.setId("100");
            user.setMobile("185");
            user.setUsername("dd");
            user.setPassword(passwordEncoder.encode("dd"));
            log.info("账号dd验证通过...");
            return User.withUsername(JSON.toJSONString(user)).password(user.getPassword()).authorities("p1","p3").build();
        }
        return null;
    }

    public static void main(String[] args) {
        String password = new BCryptPasswordEncoder().encode("secret");
        System.out.println(password);
    }
}
