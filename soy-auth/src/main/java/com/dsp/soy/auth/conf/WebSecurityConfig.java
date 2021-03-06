package com.dsp.soy.auth.conf;

import com.dsp.soy.auth.service.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**", "/vendor/**", "/fonts/**", "/images/**", "/js/**");
        web.ignoring().antMatchers("/webjars/**");
    }

    /**
     * 安全拦截机制（最重要）
     */
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

    /**
     * 认证管理器
     * 通过覆盖此方法，将configure(AuthenticationManagerBuilder)方法构造的AuthenticationManager暴露为Bean
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 自定义AuthenticationManager
     * PasswordEncoder、UserDetailService本身已经声明为bean，是可以被自动检测到的，可以不用显示注入
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService)
                .passwordEncoder(passwordEncoder);
    }

}
