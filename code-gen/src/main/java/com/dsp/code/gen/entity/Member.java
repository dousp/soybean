package com.dsp.code.gen.entity;

import java.util.StringJoiner;

public class Member {

    // 是否ID
    private boolean isId;
    // 属性名称
    private String name;
    // 对应数据库字段
    private String colName;
    // 中文名
    private String displayName;
    // java类型
    private String javaType;
    // 其他注释
    private String comment;

    @Override
    public String toString() {
        return new StringJoiner(", ", Member.class.getSimpleName() + "[", "]")
                .add("isId=" + isId)
                .add("name='" + name + "'")
                .add("colName='" + colName + "'")
                .add("displayName='" + displayName + "'")
                .add("javaType='" + javaType + "'")
                .add("comment='" + comment + "'")
                .toString();
    }

    public boolean isId() {
        return isId;
    }

    public Member setId(boolean id) {
        isId = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Member setName(String name) {
        this.name = name;
        return this;
    }

    public String getColName() {
        return colName;
    }

    public Member setColName(String colName) {
        this.colName = colName;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Member setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public String getJavaType() {
        return javaType;
    }

    public Member setJavaType(String javaType) {
        this.javaType = javaType;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public Member setComment(String comment) {
        this.comment = comment;
        return this;
    }
}
