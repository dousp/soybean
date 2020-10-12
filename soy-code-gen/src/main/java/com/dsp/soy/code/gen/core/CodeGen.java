package com.dsp.soy.code.gen.core;

public interface CodeGen {

    /**
     * 模版生成
     * @param way 模版输出方式
     */
    void make(CodeWay way);

    /**
     * 生成模版的名字
     */
    String getFileName();
}
