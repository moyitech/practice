package com.carbon.auth.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.carbon.auth.common.ExpCodeEnum;
import com.carbon.auth.exception.CommonBizException;
import com.carbon.auth.service.RedisService;
import com.carbon.auth.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 短信服务实现类
 */
@Slf4j
@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    private RedisService redisService;

    /**
     * 验证码有效期（分钟）
     */
    private static final long CODE_EXPIRE_MINUTES = 5;

    @Override
    public void sendRegisterCode(String phone) {
        // 生成6位随机验证码
        String code = RandomUtil.randomNumbers(6);
        
        // 保存到Redis，5分钟过期
        String key = String.format("sms:register:%s", phone);
        redisService.setEx(key, code, CODE_EXPIRE_MINUTES, TimeUnit.MINUTES);
        
        // TODO: 调用短信服务发送验证码
        log.info("发送注册验证码到手机号: {}, 验证码: {}", phone, code);
    }

    @Override
    public void sendForgotPasswordCode(String phone) {
        // 生成6位随机验证码
        String code = RandomUtil.randomNumbers(6);
        
        // 保存到Redis，5分钟过期
        String key = String.format("sms:forgot:password:%s", phone);
        redisService.setEx(key, code, CODE_EXPIRE_MINUTES, TimeUnit.MINUTES);
        
        // TODO: 调用短信服务发送验证码
        log.info("发送忘记密码验证码到手机号: {}, 验证码: {}", phone, code);
    }

    @Override
    public void checkValidateCode(String phone, String code, String redisKeyTemplate) {
        String key = String.format(redisKeyTemplate, phone);
        String savedCode = redisService.get(key);
        
        if (StrUtil.isEmpty(savedCode)) {
            throw new CommonBizException(ExpCodeEnum.SMS_CODE_ERROR.getCode(), "验证码已过期");
        }
        
        if (!savedCode.equals(code)) {
            throw new CommonBizException(ExpCodeEnum.SMS_CODE_ERROR);
        }
        
        // 验证通过后删除验证码
        redisService.remove(key);
    }

}

