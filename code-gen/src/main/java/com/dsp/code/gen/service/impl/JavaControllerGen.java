package com.dsp.code.gen.service.impl;

import com.dsp.code.gen.entity.Home;
import com.dsp.code.gen.entity.Member;
import com.dsp.code.gen.service.Gen;
import com.dsp.code.gen.service.Way;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaControllerGen extends BeetlGen {

    private final Home home;

    public JavaControllerGen(Home home, String tplRootPath, GroupTemplate groupTemplate) {
        super(tplRootPath, groupTemplate);
        this.home = home;
    }

    @Override
    public void make(Way way) {
        GroupTemplate gt = this.groupTemplate;
        Template template = gt.getTemplate(tplRootPath + "/controller.java");
        template.binding("basePackage", home.getBasePackage());
        template.binding("className", firstUpper(home.getClassName()));
        template.binding("classNameFL", firstLower(home.getClassName()));
        template.binding("displayName", home.getDisplayName());
        template.binding("tableName", home.getTableName());
        template.binding("author", home.getAuthor());
        List<Map<String,Object>> attrs = new ArrayList<>();
        for(Member member: home.getMembers()) {
            Map<String,Object> map = new HashMap<>();
            map.put("comment", member.getComment());
            map.put("type", member.getJavaType());
            map.put("name", member.getName());
            map.put("colName", member.getColName());
            map.put("methodName", Gen.upperFirst(member.getName()));
            map.put("isId", member.isId());
            attrs.add(map);
        }
        template.binding("attrs", attrs);
        template.binding("comment", home.getComment());
        String content = template.render();
        way.flush(this, content);
    }

    @Override
    public String getFileName() {
        return home.getClassName() + "Controller.java";
    }
}
