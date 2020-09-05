package com.dsp.code.gen.service.impl;

import com.dsp.code.gen.entity.Home;
import com.dsp.code.gen.entity.Member;
import com.dsp.code.gen.service.Gen;
import com.dsp.code.gen.service.Way;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
