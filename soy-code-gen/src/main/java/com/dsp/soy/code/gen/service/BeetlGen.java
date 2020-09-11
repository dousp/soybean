package com.dsp.soy.code.gen.service;

import com.dsp.soy.code.gen.entity.Home;
import com.dsp.soy.code.gen.entity.Member;
import com.google.common.base.CaseFormat;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     *
     * @param name 字符
     * @return 首字母大写
     */
    public static String firstUpper(String name) {
        return CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.UPPER_CAMEL).convert(name);
    }

    /**
     * 首字母大写转小写
     *
     * @param name 字符
     * @return 首字母小写
     */
    public static String firstLower(String name) {
        return CaseFormat.UPPER_CAMEL.converterTo(CaseFormat.LOWER_CAMEL).convert(name);
    }

    /**
     * 公共绑定
     *
     * @param template 模版
     * @param home     数据对象
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
        List<Map<String, Object>> attrs = new ArrayList<>();
        for (Member member : home.getMembers()) {
            Map<String, Object> map = new HashMap<>();
            map.put("comment", member.getComment());
            map.put("type", member.getJavaType());
            map.put("name", member.getName());
            map.put("colName", member.getColName());
            map.put("methodName", Gen.upperFirst(member.getName()));
            map.put("isId", member.isId());
            map.put("jdbcType", jdbcType(member.getJavaType(),member.isId()));
            attrs.add(map);
        }
        template.binding("attrs", attrs);
    }

    public static String jdbcType(String javaType, boolean isId) {
        if (isId) {
            return "BIGINT";
        }
        String tag = javaType.toLowerCase();
        switch (tag) {
            case "string":
                return "VARCHAR";
            case "bigdecimal":
                return "DECIMAL";
            case "boolean":
                return "BIT";
            case "byte":
                return "TINYINT";
            case "short":
                return "SMALLINT";
            case "int":
                return "INTEGER";
            case "long":
                return "BIGINT";
            case "float":
                return "FLOAT";
            case "double":
                return "DOUBLE";
            case "byte[]":
                return "BINARY";
            case "date":
                return "DATE";
            case "time":
                return "TIME";
            case "timestamp":
                return "TIMESTAMP";
            case "clob":
                return "CLOB";
            case "blob":
                return "BLOB";
            default:
                return "VARCHAR";
        }
    }

    public GroupTemplate getGroupTemplate() {
        return groupTemplate;
    }

    public String getTplRootPath() {
        return tplRootPath;
    }

}
