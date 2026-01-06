package com.carbon.auth.exception;

import com.carbon.auth.common.ExpCodeEnum;

/**
 * 通用业务异常
 */
public class CommonBizException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private Integer code;
    private String msg;

    public CommonBizException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public CommonBizException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public CommonBizException(ExpCodeEnum expCodeEnum) {
        super(expCodeEnum.getMsg());
        this.code = expCodeEnum.getCode();
        this.msg = expCodeEnum.getMsg();
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}

