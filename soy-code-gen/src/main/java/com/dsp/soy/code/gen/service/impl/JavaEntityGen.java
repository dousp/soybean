package com.dsp.soy.code.gen.service.impl;

import com.dsp.soy.code.gen.entity.Home;
import com.dsp.soy.code.gen.service.BeetlGen;
import com.dsp.soy.code.gen.service.Way;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;

public class JavaEntityGen extends BeetlGen {

    private final Home home;

    public JavaEntityGen(Home home, String tplRootPath, GroupTemplate groupTemplate) {
        super(tplRootPath, groupTemplate);
        this.home = home;
    }

    @Override
    public void make(Way way) {
        GroupTemplate gt = this.groupTemplate;
        Template template = gt.getTemplate(tplRootPath + "/entity.java");
        binding(template, home);
        // String srcHead ="";
        // srcHead+="import java.math.*;"+LINE_SEPARATOR;
        // srcHead+="import java.util.Date;"+LINE_SEPARATOR;
        // srcHead+="import java.sql.Timestamp;"+LINE_SEPARATOR;
        // template.binding("imports", srcHead);
        String content = template.render();
        way.flush(this, content);
    }

    @Override
    public String getFileName() {
        return home.getClassName() + ".java";
    }
}