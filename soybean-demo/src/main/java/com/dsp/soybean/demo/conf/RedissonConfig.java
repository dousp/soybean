package com.dsp.soybean.demo.conf;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author dsp
 * @date 2020-08-02
 */
@Configuration
public class RedissonConfig {

    // @Resource
    // private Environment env;
    // private String getActiveName(){
    //     String[] profiles = env.getActiveProfiles();
    //     if(profiles.length > 0) {
    //         return  "-" + profiles[0];
    //     }
    //     return "";
    // }

    @Value("${spring.profiles.active}")
    private String active;

    @Bean
    public RedissonClient redisson() throws IOException {
        // 读取使用Config.fromYAML，如果是Json文件，则使用Config.fromJSON
        String suffix = null == active || "".equals(active) ? "" : "-" + active;
        Config config = Config.fromYAML(RedissonConfig.class.getClassLoader().getResource(String.join("","redisson" , suffix , ".yml")));
        return Redisson.create(config);
    }



}
