package com.dsp.soy.code.gen.util;

import com.google.common.base.CaseFormat;

public class CamelNameUtil {

    /**
     * 首字母小写转大写
     *
     * @param name 字符
     * @return 首字母大写
     */
    public static String toFirstUpper(String name) {
        return CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.UPPER_CAMEL).convert(name);
    }

    /**
     * 首字母大写转小写
     *
     * @param name 字符
     * @return 首字母小写
     */
    public static String toFirstLower(String name) {
        return CaseFormat.UPPER_CAMEL.converterTo(CaseFormat.LOWER_CAMEL).convert(name);
    }

    /**
     * 转为驼峰命名
     *
     * @param name 字符
     * @return 首字母小写
     */
    public static String toJavaName(String name) {
        return CaseFormat.LOWER_UNDERSCORE.converterTo(CaseFormat.LOWER_CAMEL).convert(name);
    }

    /**
     * 转为驼峰命名
     *
     * @param name 字符
     * @return 首字母小写
     */
    public static String toJavaNameUpper(String name) {
        return CaseFormat.LOWER_UNDERSCORE.converterTo(CaseFormat.UPPER_CAMEL).convert(name);
    }
}
