package com.dsp.soy.uaa.conf;

import com.dsp.soy.uaa.exception.UaaAccessDeniedHandler;
import com.dsp.soy.uaa.exception.UaaAuthExceptionEntryPoint;
import com.dsp.soy.uaa.exception.UaaWebResponseExceptionTranslator;
import com.dsp.soy.uaa.filter.UaaClientCredentialsTokenEndpointFilter;
import com.dsp.soy.uaa.grant.EmailTokenGranter;
import com.dsp.soy.uaa.grant.SmsTokenGranter;
import com.dsp.soy.uaa.model.User;
import com.dsp.soy.uaa.service.EmailUserDetailsService;
import com.dsp.soy.uaa.service.SmsUserDetailsService;
import com.dsp.soy.uaa.service.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.security.KeyPair;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author dsp
 * @date 2020-10-04
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Resource
    SecurityProperties securityProperties;
    @Resource
    DataSource dataSource;
    @Resource
    AuthenticationManager authenticationManager;
    @Resource
    SmsUserDetailsService smsUserDetailsService;
    @Resource
    EmailUserDetailsService emailUserDetailsService;
    @Resource
    UaaWebResponseExceptionTranslator uaaWebResponseExceptionTranslator;
    @Resource
    UaaAccessDeniedHandler uaaAccessDeniedHandler;
    @Resource
    UaaAuthExceptionEntryPoint uaaAuthExceptionEntryPoint;
    @Resource
    UserDetailServiceImpl userDetailService;


    /**
     * 客户端详情服务
     * 客户端信息存储到数据库
     * 授权类型，"authorization_code","password","client_credentials","implicit","refresh_token"
     * 如果需要启用password授权模式，需要特别为AuthorizationServerEndpointsConfigurer提供AuthenticationManager
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataSource);
        jdbcClientDetailsService.setPasswordEncoder(passwordEncoder());
        clients.withClientDetails(jdbcClientDetailsService);
    }

    /**
     * 认证服务端点设置
     * /oauth/authorize ：授权端点。
     * /oauth/token ：令牌端点。
     * /oauth/confirm_access ：用户确认授权提交端点。
     * /oauth/error ：授权服务错误信息端点。
     * /oauth/check_token ：用于资源服务访问的令牌解析端点。
     * /oauth/token_key ：提供公有密匙的端点，如果你使用JWT令牌的话
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                // 用户详情
                .userDetailsService(userDetailService)
                // 认证管理器
                .authenticationManager(authenticationManager)
                // 令牌管理服务
                .tokenServices(authorizationServerTokenServices())
                // 允许使用post请求获取token
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .exceptionTranslator(uaaWebResponseExceptionTranslator)
        ;
    }

    /**
     * 认证服务安全设置
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        UaaClientCredentialsTokenEndpointFilter filter = new UaaClientCredentialsTokenEndpointFilter(security);
        filter.afterPropertiesSet();
        filter.setAuthenticationEntryPoint(uaaAuthExceptionEntryPoint);
        security
                // oauth/token_key公开
                .tokenKeyAccess("permitAll()")
                // oauth/check_token公开
                .checkTokenAccess("permitAll()")
                .accessDeniedHandler(uaaAccessDeniedHandler)
                // 表单认证（申请令牌）,会默认添加系统自带的ClientCredentialsTokenEndpointFilter
                // 导致自定义的UaaClientCredentialsTokenEndpointFilter失效
                // 自定义的filter优先级在系统自带filter之后
                // .allowFormAuthenticationForClients()
                .addTokenEndpointAuthenticationFilter(filter)
        ;

    }

    /**
     * 令牌管理服务
     */
    @Bean
    public AuthorizationServerTokenServices authorizationServerTokenServices() {
        DefaultTokenServices service = new DefaultTokenServices();
        service.setSupportRefreshToken(true);
        // refresh_token使用一次后失效
        service.setReuseRefreshToken(false);
        service.setTokenStore(tokenStore());
        service.setTokenEnhancer(tokenEnhancerChain());
        // 刷新令牌默认有效期
        service.setRefreshTokenValiditySeconds((int) TimeUnit.MINUTES.toSeconds(securityProperties.getAccessTokenValidityMinutes()));
        // 令牌默认有效期
        service.setAccessTokenValiditySeconds((int) TimeUnit.MINUTES.toSeconds(securityProperties.getAccessTokenValidityMinutes()));
        return service;
    }

    /**
     * 令牌存储方式 : JdbcTokenStore
     */
    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    /**
     * token增强
     */
    @Bean
    public TokenEnhancerChain tokenEnhancerChain() {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), jwtAccessTokenConverter()));
        return tokenEnhancerChain;
    }

    /**
     * JWT内容增强
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            Map<String, Object> map = new HashMap<>(2);
            User user = (User) authentication.getUserAuthentication().getPrincipal();
            map.put("userId", user.getId());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(map);
            return accessToken;
        };
    }

    /**
     * 在JWT编码的令牌值和OAuth身份验证信息（双向）之间转换的助手
     * 授予令牌时，还充当TokenEnhancer
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        // 对称秘钥，资源服务器使用该秘钥来验证
        // converter.setSigningKey(signingKey);
        // 非对称秘钥
        converter.setKeyPair(keyPair());
        return converter;
    }

    /**
     * 从classpath下的密钥库中获取密钥对(公钥+私钥)
     */
    @Bean
    public KeyPair keyPair() {
        KeyStoreKeyFactory factory = new KeyStoreKeyFactory(
                new ClassPathResource(securityProperties.getKeyName()), securityProperties.getSigningKey().toCharArray());
        return factory.getKeyPair(securityProperties.getKeyAlias(), securityProperties.getSigningKey().toCharArray());
    }

    /**
     * 密码编码器
     * 声明bean后，这个会默认应用到用户密码校验上
     * 也可以通过AuthenticationManagerBuilder自定义
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 先获取已经有的五种授权，然后添加我们自己的进去
     *
     * @param endpoints AuthorizationServerEndpointsConfigurer
     * @return TokenGranter
     */
    private TokenGranter tokenGranter(final AuthorizationServerEndpointsConfigurer endpoints) {
        List<TokenGranter> granters = new ArrayList<>(Collections.singletonList(endpoints.getTokenGranter()));
        granters.add(new SmsTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory(), smsUserDetailsService));
        granters.add(new EmailTokenGranter(endpoints.getTokenServices(), endpoints.getClientDetailsService(),
                endpoints.getOAuth2RequestFactory(), emailUserDetailsService));
        return new CompositeTokenGranter(granters);
    }
}