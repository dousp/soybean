package com.dsp.code.gen;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;

@SpringBootTest
class CodeGenApplicationTests {

    @Resource
    GroupTemplate groupTemplate;

    @Test
    void contextLoads() {
        Template t = groupTemplate.getTemplate("demo/demo2.tpl");
        t.binding("name", "Dou");
        String str = t.render();
        System.out.println(str);
    }

    @Test
    void testTpl() throws IOException {
        ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader("templates");
        Configuration cfg = Configuration.defaultConfiguration();
        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
        Template t = gt.getTemplate("/demo1.tpl");
        t.binding("name", "Dou");
        String str = t.render();
        System.out.println(str);
    }

}
