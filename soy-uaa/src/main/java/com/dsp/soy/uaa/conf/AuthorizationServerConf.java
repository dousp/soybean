package com.dsp.soy.uaa.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.annotation.Resource;

/**
 * @author dsp
 * @date 2020-10-04
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConf extends AuthorizationServerConfigurerAdapter {

    @Resource
    private AuthenticationManager authenticationManager;
    // @Resource
    // private AuthorizationServerTokenServices authorizationServerTokenServices;
    // @Resource
    // private AuthorizationCodeServices authorizationCodeServices;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private ClientDetailsService clientDetailsService;


    private final String SIGNING_KEY = "uaa123";


    // 令牌存储方式
    @Bean
    public TokenStore tokenStore() {
        //使用内存存储令牌（普通令牌）
        return new InMemoryTokenStore();
        // jwt store
        // return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        //对称秘钥，资源服务器使用该秘钥来验证
        converter.setSigningKey(SIGNING_KEY);
        return converter;
    }


    // 令牌管理服务
    @Bean
    public AuthorizationServerTokenServices authorizationServerTokenServices() {
        DefaultTokenServices service=new DefaultTokenServices();
        service.setClientDetailsService(clientDetailsService);// 客户端详情服务
        service.setSupportRefreshToken(true);// 支持刷新令牌
        service.setTokenStore(tokenStore());// 令牌存储策略
        //令牌增强
        // TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        // tokenEnhancerChain.setTokenEnhancers(Collections.singletonList(accessTokenConverter()));
        // service.setTokenEnhancer(tokenEnhancerChain);

        service.setAccessTokenValiditySeconds(7200); // 令牌默认有效期2小时
        service.setRefreshTokenValiditySeconds(259200); // 刷新令牌默认有效期3天
        return service;
    }

    // 设置授权码模式的授权码如何存取，暂时采用内存方式
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new InMemoryAuthorizationCodeServices();
    }

    // @Bean
    // public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource) {
    //     return new JdbcAuthorizationCodeServices(dataSource);//设置授权码模式的授权码如何存取
    // }

    // 将客户端信息存储到数据库
    // @Bean
    // public ClientDetailsService clientDetailsService(DataSource dataSource) {
    //     JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataSource);
    //     jdbcClientDetailsService.setPasswordEncoder(passwordEncoder);
    //     return jdbcClientDetailsService;
    // }


    // 客户端详情服务
    @Override
    public void configure(ClientDetailsServiceConfigurer clients)
            throws Exception {
        // clients.withClientDetails(clientDetailsService);
        // 使用in-memory存储
        clients.inMemory()
                // client_id
                .withClient("c1")
                // 客户端密钥
                .secret(new BCryptPasswordEncoder().encode("secret"))
                // 资源列表
                .resourceIds("res1")
                // 该client允许的授权类型authorization_code,password,refresh_token,implicit,client_credentials
                .authorizedGrantTypes("authorization_code", "password","client_credentials","implicit","refresh_token")
                // 允许的授权范围
                .scopes("all")
                // false跳转到授权页面
                .autoApprove(false)
                // 加上验证回调地址
                .redirectUris("http://www.baidu.com")
        ;
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
                // 认证管理器
                .authenticationManager(authenticationManager)
                // 授权码服务
                .authorizationCodeServices(authorizationCodeServices())
                // 令牌管理服务
                .tokenServices(authorizationServerTokenServices())
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }

    /**
     * 认证服务安全设置
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security){
        security
                // oauth/token_key是公开
                .tokenKeyAccess("permitAll()")
                // oauth/check_token公开
                .checkTokenAccess("permitAll()")
                // 表单认证（申请令牌）
                .allowFormAuthenticationForClients()
        ;
    }




}
