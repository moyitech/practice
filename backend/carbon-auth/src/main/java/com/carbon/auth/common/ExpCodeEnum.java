package com.carbon.auth.common;

/**
 * 异常代码枚举
 */
public enum ExpCodeEnum {

    /**
     * 系统异常
     */
    SYSTEM_ERROR(500, "系统异常"),

    /**
     * 用户不存在
     */
    SYSTEM_SECURITY_USER_NULL(1001, "用户不存在或密码错误"),

    /**
     * 密码错误
     */
    SYSTEM_SECURITY_USER_PASSWORD_ERROR(1002, "密码错误"),

    /**
     * 账号已被禁用
     */
    SYS_ACCOUNT_DISABLE(1003, "账号已被禁用"),

    /**
     * 账号未开户
     */
    SYS_ACCOUNT_NO_OPENED(1004, "账号未开户，请联系管理员"),

    /**
     * 验证码错误
     */
    SMS_CODE_ERROR(1005, "验证码错误"),

    /**
     * 两次密码不一致
     */
    PASSWORD_NOT_SAME(1006, "两次输入的密码不一致"),

    /**
     * 注册失败
     */
    REGISTER_FAIL(1007, "注册失败"),

    /**
     * 账号已存在
     */
    ACCOUNT_EXIST(1008, "账号已存在");

    private final Integer code;
    private final String msg;

    ExpCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}

