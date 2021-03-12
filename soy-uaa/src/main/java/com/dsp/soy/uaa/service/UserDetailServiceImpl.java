package com.dsp.soy.uaa.service;

import com.dsp.soy.uaa.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;

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
        User user = new User();
        if(username.equals("dd")){
            user.setId(100L);
            user.setMobile("185");
            user.setUsername("dd");
            user.setPassword("$2a$10$pWhkoU/NjtDk41DpfI2EaedP4W3jM8GX7a1sUEY6tIMavKduTq7kO");
            user.setAuthorities(Arrays.asList(new SimpleGrantedAuthority("p1"),new SimpleGrantedAuthority("p3")));
            log.info("账号dd验证通过...");
            return user;
        }
        if(null == user.getId()){
            throw new RuntimeException("未找到该账户");
        } else if (!user.isEnabled()) {
            throw new DisabledException("该账户已被禁用!");
        } else if (!user.isAccountNonLocked()) {
            throw new LockedException("该账号已被锁定!");
        } else if (!user.isAccountNonExpired()) {
            throw new AccountExpiredException("该账号已过期!");
        }
        return null;
    }

    public static void main(String[] args) {
        // String password = new BCryptPasswordEncoder().encode("secret");
        String password = new BCryptPasswordEncoder().encode("dd");
        System.out.println(password);
    }
}
