package com.dsp.code.gen.service.impl;

import com.dsp.code.gen.entity.Home;
import com.dsp.code.gen.service.BeetlGen;
import com.dsp.code.gen.service.Way;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;

public class JavaMapperGen extends BeetlGen {

    private final Home home;

    public JavaMapperGen(Home home, String tplRootPath, GroupTemplate groupTemplate) {
        super(tplRootPath, groupTemplate);
        this.home = home;
    }

    @Override
    public void make(Way way) {
        GroupTemplate gt = this.groupTemplate;
        Template template = gt.getTemplate(tplRootPath + "/mapper.java");
        binding(template, home);
        String content = template.render();
        way.flush(this, content);
    }

    @Override
    public String getFileName() {
        return home.getClassName() + "Mapper.java";
    }
}
