package com.dsp.soy.common.result;

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

    AUTHORIZED_ERROR("A0300", "访问权限异常"),
    ACCESS_UNAUTHORIZED("A0301", "访问未授权"),
    FORBIDDEN_OPERATION("A0302", "READ模式禁止修改、删除重要数据，请本地部署后测试"),

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
