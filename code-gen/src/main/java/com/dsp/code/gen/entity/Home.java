package com.dsp.code.gen.entity;

import java.util.ArrayList;
import java.util.StringJoiner;

public class Home {
    // 包名
    private String packageName;
    // 实体名，作为所有类的前缀
    private String entityName;
    // 对应数据库表的名称
    private String tableName;
    // 实体名称、功能名称
    private String displayName;
    // 路由路径，controller等路径标识
    private String rootPath;
    // 实体类相关字段
    private ArrayList<Member>  members;


    @Override
    public String toString() {
        return new StringJoiner(", ", Home.class.getSimpleName() + "[", "]")
                .add("packageName='" + packageName + "'")
                .add("entityName='" + entityName + "'")
                .add("tableName='" + tableName + "'")
                .add("displayName='" + displayName + "'")
                .add("rootPath='" + rootPath + "'")
                .add("members=" + members)
                .toString();
    }

    public String getPackageName() {
        return packageName;
    }

    public Home setPackageName(String packageName) {
        this.packageName = packageName;
        return this;
    }

    public String getEntityName() {
        return entityName;
    }

    public Home setEntityName(String entityName) {
        this.entityName = entityName;
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

    public ArrayList<Member> getMembers() {
        return members;
    }

    public Home setMembers(ArrayList<Member> members) {
        this.members = members;
        return this;
    }
}
