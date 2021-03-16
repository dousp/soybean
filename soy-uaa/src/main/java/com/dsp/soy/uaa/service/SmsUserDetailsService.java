package com.dsp.soy.uaa.service;

import com.dsp.soy.uaa.exception.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;

public interface SmsUserDetailsService {

    /**
     * @param sms sms
     * @return 用户认证信息
     * @throws ResourceNotFoundException 用户名未找到
     */
    UserDetails loadUserBySms(String sms) throws ResourceNotFoundException;
}
