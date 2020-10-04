package com.dsp.soy.uaa.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author dsp
 * @date 2020-10-04
 */
@Component
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
            ObjectMapper objectMapper = new ObjectMapper();
            String principal = null;
            try {
                principal = objectMapper.writeValueAsString(user);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return User.withUsername(principal).password(user.getPassword()).authorities("p1","p3").build();
        }
        return null;
    }
}