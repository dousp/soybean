package com.dsp.code.gen.service.impl;

import com.dsp.code.gen.service.Gen;
import com.dsp.code.gen.service.Way;
import com.google.common.base.CaseFormat;
import org.beetl.core.GroupTemplate;

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

    public GroupTemplate getGroupTemplate() {
        return groupTemplate;
    }

    public String getTplRootPath() {
        return tplRootPath;
    }

}
