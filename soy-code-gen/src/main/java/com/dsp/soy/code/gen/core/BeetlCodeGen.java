package com.dsp.soy.code.gen.core;

import com.dsp.soy.code.gen.entity.Home;
import com.dsp.soy.code.gen.entity.Member;
import com.dsp.soy.code.gen.util.CamelNameUtil;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BeetlCodeGen implements CodeGen {

    protected String tplPath;
    protected GroupTemplate gt;
    protected Home home;

    public BeetlCodeGen(GroupTemplate gt, Home home, String tplPath) {
        this.tplPath = tplPath;
        this.gt = gt;
        this.home = home;
    }

    @Override
    public void make(CodeWay way) {
        Template template = getTpl();
        binding(template, home);
        way.flush(this, template.render());
    }

    @Override
    public String getFileName() {
        return home.getClassName() + "." + home.getSuffix();
    }

    public Template getTpl() {
        return this.gt.getTemplate(this.tplPath);
    }

    /**
     * 公共绑定
     *
     * @param template 模版
     */
    public static void binding(Template template, Home home) {
        // 不直接使用Home对象，方便绑定额外计算出来的属性
        template.binding("basePackage", home.getBasePackage());
        template.binding("className", CamelNameUtil.toFirstUpper(home.getClassName()));
        template.binding("classNameFL", CamelNameUtil.toFirstLower(home.getClassName()));
        template.binding("displayName", home.getDisplayName());
        template.binding("tableName", home.getTableName());
        template.binding("author", home.getAuthor());
        template.binding("comment", home.getComment());
        List<Map<String, Object>> attrs = new ArrayList<>();
        for (Member member : home.getMembers()) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", member.getName());
            map.put("colName", member.getColName());
            map.put("isId", member.isId());
            map.put("type", member.getJavaType());
            map.put("jdbcType", member.getJdbcType());
            map.put("methodName", upperFirst(member.getName()));
            map.put("comment", member.getComment());
            attrs.add(map);
        }
        template.binding("attrs", attrs);
    }

    public static String upperFirst(String s) {
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return Character.toUpperCase(s.charAt(0)) + s.substring(1);
        }
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

    public static String javaType(String jdbcType, boolean isId) {
        if (isId) {
            return "Long";
        }
        String tag = jdbcType.toUpperCase();
        switch (tag) {
            case "DECIMAL":
                return "BigDecimal";
            case "FLOAT":
                return "Float";
            case "DOUBLE":
                return "Double";
            case "BIT":
                return "boolean";
            case "TINYINT":
                return "byte";
            case "SMALLINT":
                // return "short";
            case "INTEGER":
            case "INT":
                return "Integer";
            case "BIGINT":
                return "Long";
            case "BINARY":
                return "byte[]";
            case "DATE":
            case "TIME":
            case "TIMESTAMP":
                return "Date";
            case "VARCHAR":
            case "CLOB":
            case "BLOB":
            default:
                return "String";
        }
    }

    public GroupTemplate getGt() {
        return gt;
    }

    public String getTplPath() {
        return tplPath;
    }

    public Home getHome() {
        return home;
    }
}
