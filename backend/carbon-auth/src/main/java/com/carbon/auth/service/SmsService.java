package com.carbon.auth.service;

/**
 * 短信服务接口
 */
public interface SmsService {

    /**
     * 发送注册验证码
     *
     * @param phone 手机号
     */
    void sendRegisterCode(String phone);

    /**
     * 发送忘记密码验证码
     *
     * @param phone 手机号
     */
    void sendForgotPasswordCode(String phone);

    /**
     * 校验验证码
     *
     * @param phone      手机号
     * @param code       验证码
     * @param redisKey   Redis键模板
     */
    void checkValidateCode(String phone, String code, String redisKey);

}

