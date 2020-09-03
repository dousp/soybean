package com.dsp.code.gen.service;

public interface Gen {

    /**
     * 模版生成
     * @param way 模版输出方式
     */
    void make(Way way);

    /**
     * 生成模版的名字
     */
    String getFileName();

    static String upperFirst(String s) {
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return Character.toUpperCase(s.charAt(0)) + s.substring(1);
        }
    }
}
