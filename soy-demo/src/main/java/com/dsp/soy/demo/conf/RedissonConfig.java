package com.dsp.soy.demo.conf;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
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
        String fileName = String.join("","redisson-single" , suffix , ".yaml");
        Config config = Config.fromYAML(RedissonConfig.class.getClassLoader().getResource(fileName));
        return Redisson.create(config);
    }



}
