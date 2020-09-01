package com.dsp.code.gen.service.impl;

import com.dsp.code.gen.service.Gen;
import com.dsp.code.gen.service.Way;
import org.beetl.core.GroupTemplate;

import javax.annotation.Resource;

public class BeetlWay implements Way {

    @Resource
    GroupTemplate GroupTemplate;

    @Override
    public void flush(Gen gen, String content) {

    }
}
