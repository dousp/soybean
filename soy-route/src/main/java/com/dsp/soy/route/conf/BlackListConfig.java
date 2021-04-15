package com.dsp.soy.route.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 黑名单配置
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "blacklist")
public class BlackListConfig {

    private List<String> urls;

}
