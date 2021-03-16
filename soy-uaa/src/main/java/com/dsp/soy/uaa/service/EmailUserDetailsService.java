package com.dsp.soy.uaa.service;

import com.dsp.soy.uaa.exception.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;

public interface EmailUserDetailsService {

    /**
     * 通过邮件加载用户信息
     * @param email email
     * @return 用户认证信息
     * @throws ResourceNotFoundException 用户名未找到
     */
    UserDetails loadUserByEmail(String email) throws ResourceNotFoundException;
}
