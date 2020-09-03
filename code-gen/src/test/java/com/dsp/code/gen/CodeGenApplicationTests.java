package com.dsp.code.gen;

import com.dsp.code.gen.entity.Home;
import com.dsp.code.gen.entity.Member;
import com.dsp.code.gen.service.impl.BeetlWay;
import com.dsp.code.gen.service.impl.JavaEntityGen;
import com.ibeetl.starter.BeetlTemplateCustomize;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;

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

    @Test
    void testJavaGen(){

        ArrayList<Member> members = new ArrayList<>();
        Member member = new Member();
        member.setId(true);
        member.setName("id");
        member.setColName("id");
        member.setDisplayName("ID");
        member.setJavaType("Long");
        member.setComment("我是PK");

        Member member2 = new Member();
        member2.setId(false);
        member2.setName("fileClass");
        member2.setColName("file_class");
        member2.setDisplayName("文件处理");
        member2.setJavaType("String");
        member2.setComment("我只版");

        members.add(member);
        members.add(member2);


        Home home = new Home();
        home.setBasePackage("com.dsp.gen");
        home.setClassName("GenTest");
        home.setTableName("gen_test");
        home.setDisplayName("生成器");
        home.setRootPath("gen");
        home.setMembers(members);
        home.setComment("test.....");


        JavaEntityGen entityGen = new JavaEntityGen(home,"/beetl/java", groupTemplate);
        BeetlWay way = new BeetlWay();
        entityGen.make(way);
    }

}
