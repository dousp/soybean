package com.dsp.soy.code.gen;

import com.dsp.soy.code.gen.entity.Home;
import com.dsp.soy.code.gen.entity.Member;
import com.dsp.soy.code.gen.service.BeetlGen;
import com.dsp.soy.code.gen.service.BeetlWay;
import com.dsp.soy.code.gen.service.impl.JavaControllerGen;
import com.dsp.soy.code.gen.service.impl.JavaEntityGen;
import com.dsp.soy.code.gen.service.impl.JavaMapperGen;
import com.dsp.soy.code.gen.service.impl.JavaServiceGen;
import com.dsp.soy.code.gen.service.impl.JavaSqlProviderGen;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

@SpringBootTest
class SoyCodeGenApplicationTests {

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
    void testUpper(){
        System.out.println(BeetlGen.firstLower("Class"));
        System.out.println(BeetlGen.firstUpper("Class"));
    }

    @Test
    void encoding(){
        //获取系统默认编码
        System.out.println("系统默认编码：" + System.getProperty("file.encoding")); //查询结果GBK
        //系统默认字符编码
        System.out.println("系统默认字符编码：" + Charset.defaultCharset()); //查询结果GBK
        //操作系统用户使用的语言
        System.out.println("系统默认语言：" + System.getProperty("user.language")); //查询结果zh
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
        members.add(member2);
        members.add(member2);
        members.add(member2);
        members.add(member2);

        Home home = new Home();
        home.setBasePackage("com.dsp.gen");
        home.setClassName("GenTest");
        home.setTableName("gen_test");
        home.setDisplayName("生成器");
        home.setRootPath("gen");
        home.setMembers(members);
        home.setComment("test.....");

        BeetlWay way = new BeetlWay();
        JavaEntityGen entityGen = new JavaEntityGen(home,"/beetl/java", groupTemplate);
        entityGen.make(way);
        JavaMapperGen mapperGen = new JavaMapperGen(home,"/beetl/java", groupTemplate);
        mapperGen.make(way);
        JavaSqlProviderGen sqlProviderGen = new JavaSqlProviderGen(home,"/beetl/java", groupTemplate);
        sqlProviderGen.make(way);
        JavaServiceGen serviceGen = new JavaServiceGen(home,"/beetl/java", groupTemplate);
        serviceGen.make(way);
        JavaControllerGen controllerGen = new JavaControllerGen(home,"/beetl/java", groupTemplate);
        controllerGen.make(way);

    }

}
