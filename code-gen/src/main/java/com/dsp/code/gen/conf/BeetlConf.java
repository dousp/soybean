package com.dsp.code.gen.conf;


import org.beetl.core.GroupTemplate;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.beetl.ext.spring.BeetlGroupUtilConfiguration;
import org.beetl.ext.spring.BeetlSpringViewResolver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class BeetlConf {

    //模板根目录
    @Value("${beetl.templatesPath}")
    String templatesPath;

    @Bean(name = "groupTemplate")
    public GroupTemplate getGroupTemplate(@Qualifier("beetlConfig") BeetlGroupUtilConfiguration beetlGroupUtilConfiguration) {
        GroupTemplate gt = beetlGroupUtilConfiguration.getGroupTemplate();
        org.beetl.core.Configuration cfg;
        try {
            cfg = org.beetl.core.Configuration.defaultConfiguration();
            cfg.setStatementStart("@");
            cfg.setStatementEnd(null);
            cfg.setPlaceholderStart("${");
            cfg.setPlaceholderEnd("}");
            cfg.setHtmlTagSupport(false);
            cfg.build();
            gt.setConf(cfg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gt;
    }

    @Bean(name = "beetlConfig")
    public BeetlGroupUtilConfiguration getBeetlGroupUtilConfiguration() {
        BeetlGroupUtilConfiguration beetlGroupUtilConfiguration = new BeetlGroupUtilConfiguration();
        //获取Spring Boot 的ClassLoader
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        if(loader==null){
            loader = BeetlConf.class.getClassLoader();
        }
        beetlGroupUtilConfiguration.setResourceLoader(new ClasspathResourceLoader(loader, templatesPath));
        //额外的配置，可以覆盖默认配置，一般不需要
        // beetlGroupUtilConfiguration.setConfigProperties(extProperties);
        beetlGroupUtilConfiguration.init();
        //如果使用了优化编译器，涉及到字节码操作，需要添加ClassLoader
        // beetlGroupUtilConfiguration.getGroupTemplate().setClassLoader(loader);
        return beetlGroupUtilConfiguration;
    }

    @Bean(name = "beetlViewResolver")
    public BeetlSpringViewResolver getBeetlSpringViewResolver(@Qualifier("beetlConfig") BeetlGroupUtilConfiguration beetlGroupUtilConfiguration) {
        BeetlSpringViewResolver beetlSpringViewResolver = new BeetlSpringViewResolver();
        beetlSpringViewResolver.setContentType("text/html;charset=UTF-8");
        beetlSpringViewResolver.setOrder(0);
        beetlSpringViewResolver.setConfig(beetlGroupUtilConfiguration);
        // 这里并没有配置后缀，因此controller代码里必须显式的加上后缀

        return beetlSpringViewResolver;
    }

}