package com.dsp.soy.uaa.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {

    private String code;

    private T data;

    private String msg;

    private String detail;

    // @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer total;

    private String path;

    private static <T> Result<T> result(IResultCode resultCode, T data) {
        return result(resultCode.getCode(), resultCode.getMsg(), data);
    }

    public static <T> Result<T> result(String code, String msg) {
        return result(code,msg,null);
    }

    private static <T> Result<T> result(String code, String msg, T data) {
        Result<T> result = new Result<T>();
        result.setCode(code);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        ResultCode rce = ResultCode.SUCCESS;
        return result(rce, data);
    }

    public static <T> Result<T> success(T data, Long total) {
        Result<T> result = success(data);
        result.setTotal(total.intValue());
        return result;
    }

    public static <T> Result<T> failed() {
        return result(ResultCode.SYSTEM_EXECUTION_ERROR.getCode(), ResultCode.SYSTEM_EXECUTION_ERROR.getMsg(), null);
    }

    public static <T> Result<T> failed(String msg) {
        return result(ResultCode.SYSTEM_EXECUTION_ERROR.getCode(), msg, null);
    }

    public static <T> Result<T> failed(IResultCode resultCode) {
        return result(resultCode.getCode(), resultCode.getMsg(), null);
    }

    public static <T> Result<T> judge(boolean status) {
        if (status) {
            return success();
        } else {
            return failed();
        }
    }



}
