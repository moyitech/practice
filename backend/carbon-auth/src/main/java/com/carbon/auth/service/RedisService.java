package com.carbon.auth.service;

import java.util.concurrent.TimeUnit;

/**
 * Redis服务接口
 */
public interface RedisService {

    /**
     * 设置缓存
     *
     * @param key   键
     * @param value 值
     */
    void set(String key, String value);

    /**
     * 设置缓存（带过期时间）
     *
     * @param key      键
     * @param value    值
     * @param timeout  过期时间
     * @param timeUnit 时间单位
     */
    void setEx(String key, String value, long timeout, TimeUnit timeUnit);

    /**
     * 获取缓存
     *
     * @param key 键
     * @return 值
     */
    String get(String key);

    /**
     * 删除缓存
     *
     * @param key 键
     */
    void remove(String key);

    /**
     * 判断键是否存在
     *
     * @param key 键
     * @return 是否存在
     */
    Boolean hasKey(String key);

}

