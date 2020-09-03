package com.dsp.code.gen.service.impl;

import com.dsp.code.gen.service.Gen;
import com.dsp.code.gen.service.Way;
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

    public GroupTemplate getGroupTemplate() {
        return groupTemplate;
    }

    public String getTplRootPath() {
        return tplRootPath;
    }

}
