package com.dsp.soy.code.gen;

import com.dsp.soy.code.gen.core.BeetlCodeWay;
import com.dsp.soy.code.gen.core.JavaCodeGen;
import com.dsp.soy.code.gen.entity.Home;
import com.dsp.soy.code.gen.entity.Member;
import com.dsp.soy.code.gen.service.TableService;
import com.dsp.soy.code.gen.util.CamelNameUtil;
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
import java.util.List;

@SpringBootTest
class SoyCodeGenApplicationTests {

    @Resource
    GroupTemplate groupTemplate;
    @Resource
    TableService tableService;

    // @Test
    void contextLoads() {
        Template t = groupTemplate.getTemplate("demo/demo2.tpl");
        t.binding("name", "Dou");
        String str = t.render();
        System.out.println(str);
    }

    // @Test
    void testTpl() throws IOException {
        ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader("templates");
        Configuration cfg = Configuration.defaultConfiguration();
        GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
        Template t = gt.getTemplate("/demo1.tpl");
        t.binding("name", "Dou");
        String str = t.render();
        System.out.println(str);
    }

    // @Test
    void testUpper() {
        System.out.println(CamelNameUtil.toFirstLower("Class"));
        System.out.println(CamelNameUtil.toFirstUpper("Class"));
        System.out.println(CamelNameUtil.toJavaNameUpper("class_dsd"));
        String str = "table_name";
        System.out.println(CamelNameUtil.toJavaNameUpper(str.substring(str.indexOf("_"))));
    }

    // @Test
    void encoding() {
        //获取系统默认编码
        System.out.println("系统默认编码：" + System.getProperty("file.encoding")); //查询结果GBK
        //系统默认字符编码
        System.out.println("系统默认字符编码：" + Charset.defaultCharset()); //查询结果GBK
        //操作系统用户使用的语言
        System.out.println("系统默认语言：" + System.getProperty("user.language")); //查询结果zh
    }

    // @Test
    public void testGen() {

        String tableSchema = "tjdb1";
        String tableName = "rbac_role";
        String className = CamelNameUtil.toJavaNameUpper(tableName.substring(tableName.indexOf("_")));
        String comment = "code gen";
        String basePackage = "com.dsp.gen";
        String displayName = "角色";
        String genRootPath = "gen";

        List<Member> members = tableService.getAllColumns(tableSchema, tableName);

        Home home = new Home();
        home.setComment(comment);
        home.setBasePackage(basePackage);
        home.setClassName(className);
        home.setTableName(tableName);
        home.setDisplayName(displayName);
        home.setRootPath(genRootPath);
        home.setMembers(members);
    }

    // @Test
    void testJavaGen2() {

        ArrayList<Member> members = new ArrayList<>();
        Member member = new Member();
        member.setId(true);
        member.setName("id");
        member.setColName("id");
        member.setDisplayName("ID");
        member.setJavaType("Long");
        member.setJdbcType("bigint");
        member.setComment("我是PK");

        Member member2 = new Member();
        member2.setId(false);
        member2.setName("fileClass");
        member2.setColName("file_class");
        member2.setDisplayName("文件处理");
        member2.setJavaType("String");
        member2.setJdbcType("VARCHAR");
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

        JavaCodeGen entityGen = new JavaCodeGen(groupTemplate, home, "/beetl/java/entity.java");
        entityGen.make(new BeetlCodeWay());


    }

}
