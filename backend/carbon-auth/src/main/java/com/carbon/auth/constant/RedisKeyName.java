package com.carbon.auth.constant;

/**
 * Redis Key名称常量
 */
public class RedisKeyName {

    /**
     * 短信验证码 - 注册
     */
    public static final String SMS_REGISTER_KEY = "sms:register:%s";

    /**
     * 短信验证码 - 忘记密码
     */
    public static final String SMS_FORGOT_PASSWORD_KEY = "sms:forgot:password:%s";

}

