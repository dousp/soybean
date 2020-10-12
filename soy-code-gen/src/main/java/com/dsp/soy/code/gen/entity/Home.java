package com.dsp.soy.code.gen.entity;

import java.util.List;
import java.util.StringJoiner;

public class Home {
    // 作者
    private String author;
    // 包名
    private String basePackage;
    // 实体名，作为所有类的前缀
    private String className;
    // 对应数据库表的名称
    private String tableName;
    // 实体名称、功能名称
    private String displayName;
    // 注释
    private String comment;
    // 路由路径，controller等路径标识
    private String rootPath;
    // 实体类相关字段
    private List<Member> members;
    // 文件名后缀
    private String suffix = "java";


    @Override
    public String toString() {
        return new StringJoiner(", ", Home.class.getSimpleName() + "[", "]")
                .add("author='" + author + "'")
                .add("basePackage='" + basePackage + "'")
                .add("className='" + className + "'")
                .add("tableName='" + tableName + "'")
                .add("displayName='" + displayName + "'")
                .add("comment='" + comment + "'")
                .add("rootPath='" + rootPath + "'")
                .add("members=" + members)
                .add("suffix=" + suffix)
                .toString();
    }

    public String getBasePackage() {
        return basePackage;
    }

    public Home setBasePackage(String basePackage) {
        this.basePackage = basePackage;
        return this;
    }

    public String getClassName() {
        return className;
    }

    public Home setClassName(String className) {
        this.className = className;
        return this;
    }

    public String getTableName() {
        return tableName;
    }

    public Home setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Home setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public String getRootPath() {
        return rootPath;
    }

    public Home setRootPath(String rootPath) {
        this.rootPath = rootPath;
        return this;
    }

    public List<Member> getMembers() {
        return members;
    }

    public Home setMembers(List<Member> members) {
        this.members = members;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public Home setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public Home setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getSuffix() {
        return suffix;
    }

    public Home setSuffix(String suffix) {
        this.suffix = suffix;
        return this;
    }
}
