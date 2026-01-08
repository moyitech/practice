package com.carbon.system.common;

import java.io.Serializable;

import lombok.Data;

/**
 * API统一返回结果
 */
@Data
public class ApiResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer code;

    private String msg;

    private T data;

    public ApiResult() {
    }

    public ApiResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ApiResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功返回
     */
    public static <T> ApiResult<T> ok() {
        return new ApiResult<>(ApiCode.SUCCESS.getCode(), ApiCode.SUCCESS.getMsg());
    }

    /**
     * 成功返回（带数据）
     */
    public static <T> ApiResult<T> ok(T data) {
        return new ApiResult<>(ApiCode.SUCCESS.getCode(), ApiCode.SUCCESS.getMsg(), data);
    }

    /**
     * 成功返回（带消息）
     */
    public static <T> ApiResult<T> ok(String msg) {
        return new ApiResult<>(ApiCode.SUCCESS.getCode(), msg);
    }

    /**
     * 失败返回
     */
    public static <T> ApiResult<T> fail() {
        return new ApiResult<>(ApiCode.FAIL.getCode(), ApiCode.FAIL.getMsg());
    }

    /**
     * 失败返回（带消息）
     */
    public static <T> ApiResult<T> fail(String msg) {
        return new ApiResult<>(ApiCode.FAIL.getCode(), msg);
    }

    /**
     * 失败返回（带代码）
     */
    public static <T> ApiResult<T> fail(ApiCode apiCode) {
        return new ApiResult<>(apiCode.getCode(), apiCode.getMsg());
    }

    /**
     * 失败返回（带代码和消息）
     */
    public static <T> ApiResult<T> fail(Integer code, String msg) {
        return new ApiResult<>(code, msg);
    }

}





