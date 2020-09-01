package com.dsp.code.gen.service;

import com.dsp.code.gen.entity.Home;

public interface Gen {

    /**
     * 模版生成
     * @param way 模版输出方式
     * @param home 模版基础属性
     */
    void make(Way way, Home home);

    /**
     * 生成模版的名字
     */
    String getName();
}
