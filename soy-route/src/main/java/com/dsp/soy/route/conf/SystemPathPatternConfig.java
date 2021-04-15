package com.dsp.soy.route.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 白名单配置
 * 权限验证的白名单，在此列表中的请求不会做权限验证.
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "sys.path-pattern")
public class SystemPathPatternConfig {

    /**
     * 后台管理接口路径匹配
     */
    private String admin;
    /**
     * 登出路径匹配
     */
    private String logout;

}
