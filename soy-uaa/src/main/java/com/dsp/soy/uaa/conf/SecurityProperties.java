package com.dsp.soy.uaa.conf;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("uaa.security.oauth")
public class SecurityProperties {

    /**
     * 登录请求的路径，默认值 /authorization/form
     */
    private String loginProcessingUrl = "/authorization/form";

    private String loginPage = "/oauth/login";

    private String usernameParameterName = "username";

    private String passwordParameterName = "password";

    /**
     * token 有效期， 分钟为单位
     */
    private Long accessTokenValidityMinutes = 30L;

    private Long refreshTokenValidityMinutes = 180L;

    private String signingKey;

    private String keyAlias;

    private String keyName;

}
