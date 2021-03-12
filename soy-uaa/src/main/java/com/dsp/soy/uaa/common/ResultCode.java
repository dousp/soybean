package com.dsp.soy.uaa.common;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public enum ResultCode implements IResultCode, Serializable {

    SUCCESS("200", "success"),
    FAILURE("201", "failure"),

    CLIENT_AUTHENTICATION_FAILED("A0401", "客户端认证失败"),
    TOKEN_INVALID_OR_EXPIRED("A0430", "token无效或已过期"),
    TOKEN_ACCESS_FORBIDDEN("A0431", "token已被禁止访问"),

    SYSTEM_EXECUTION_ERROR("S0001", "系统内部出错"),
    SYSTEM_EXECUTION_TIMEOUT("S0002", "系统执行超时")
    ;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    private String code;

    private String msg;
}
