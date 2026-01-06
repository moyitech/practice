package com.carbon.auth.constant;

/**
 * Redis Key常量
 */
public class CommonRedisKey {

    /**
     * 登录用户信息 Key
     * 格式: login:user:{accountId}
     */
    public static final String LOGIN_USER = "login:user:%s";

}

