package com.dsp.code.gen.service.impl;

import com.dsp.code.gen.entity.Home;
import com.dsp.code.gen.entity.Member;
import com.dsp.code.gen.service.Gen;
import com.dsp.code.gen.service.Way;
import com.google.common.base.CaseFormat;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class BeetlGen implements Gen {

    static String LINE_SEPARATOR = System.getProperty("line.separator");

    protected String tplRootPath;

    protected GroupTemplate groupTemplate;


    public BeetlGen(String tplRootPath, GroupTemplate groupTemplate) {
        this.tplRootPath = tplRootPath;
        this.groupTemplate = groupTemplate;
    }

    @Override
    public void make(Way way) {

    }

    @Override
    public String getFileName() {
        return null;
    }

    /**
     * 首字母小写转大写
     * @param name 字符
     * @return 首字母大写
     */
    public static String firstUpper(String name){
        return CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.UPPER_CAMEL).convert(name);
    }

    /**
     * 首字母大写转小写
     * @param name 字符
     * @return 首字母小写
     */
    public static String firstLower(String name){
        return CaseFormat.UPPER_CAMEL.converterTo(CaseFormat.LOWER_CAMEL).convert(name);
    }

    /**
     * 公共绑定
     * @param template 模版
     * @param home 数据对象
     */
    public static void binding(Template template, Home home) {
        // 不直接使用Home对象，方便绑定额外计算出来的属性
        template.binding("basePackage", home.getBasePackage());
        template.binding("className", firstUpper(home.getClassName()));
        template.binding("classNameFL", firstLower(home.getClassName()));
        template.binding("displayName", home.getDisplayName());
        template.binding("tableName", home.getTableName());
        template.binding("author", home.getAuthor());
        template.binding("comment", home.getComment());
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
    }

    public GroupTemplate getGroupTemplate() {
        return groupTemplate;
    }

    public String getTplRootPath() {
        return tplRootPath;
    }

}
