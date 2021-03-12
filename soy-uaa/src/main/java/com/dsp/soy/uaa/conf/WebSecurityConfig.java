package com.dsp.soy.uaa.conf;

import com.dsp.soy.uaa.service.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * @author dsp
 * @date 2020-10-04
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    SecurityProperties securityProperties;
    @Resource
    UserDetailServiceImpl userDetailService;

    //认证管理器
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //密码编码器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService)
                .passwordEncoder(passwordEncoder());
    }

    //安全拦截机制（最重要）
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/getPublicKey").permitAll()
            .antMatchers("/actuator/**").permitAll()
            .antMatchers("/login*").permitAll()
            .antMatchers("/oauth/**").permitAll()
            .antMatchers(securityProperties.getLoginProcessingUrl()).permitAll()
            .anyRequest().authenticated();
        http.formLogin()
             // 登录路径
             .loginPage(securityProperties.getLoginPage())
             // 登录表单提交的路径
             .loginProcessingUrl(securityProperties.getLoginProcessingUrl())
             // 请求 {用户名} 参数名称
             .usernameParameter(securityProperties.getUsernameParameterName())
             // 请求 {密码} 参数名
             .passwordParameter(securityProperties.getPasswordParameterName())
             // .failureUrl("/login?error")
             .and();
        http.csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**","/vendor/**","/fonts/**","/images/**","/js/**");
        web.ignoring().antMatchers("/webjars/**");
    }

}
