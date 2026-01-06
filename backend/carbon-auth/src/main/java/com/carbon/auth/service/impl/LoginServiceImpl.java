package com.carbon.auth.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.bean.copier.ValueProvider;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.carbon.auth.common.ApiCode;
import com.carbon.auth.common.ApiResult;
import com.carbon.auth.common.ExpCodeEnum;
import com.carbon.auth.constant.AccountConstant;
import com.carbon.auth.constant.CommonRedisKey;
import com.carbon.auth.constant.RedisKeyName;
import com.carbon.auth.constant.RocketDelayLevelConstant;
import com.carbon.auth.constant.RocketMqName;
import com.carbon.auth.entity.SysAccount;
import com.carbon.auth.exception.CommonBizException;
import com.carbon.auth.mapper.LoginMapper;
import com.carbon.auth.param.ChangePasswordParam;
import com.carbon.auth.param.ForgotPasswordParam;
import com.carbon.auth.param.LoginParam;
import com.carbon.auth.param.RegisterParam;
import com.carbon.auth.param.SysAccountParam;
import com.carbon.auth.service.LoginService;
import com.carbon.auth.service.RedisService;
import com.carbon.auth.service.SmsService;
import com.carbon.auth.service.feign.SystemServiceApi;
import com.carbon.auth.util.HttpContextUtils;
import com.carbon.auth.util.JwtUtil;
import com.carbon.auth.vo.LoginInfoVo;
import com.carbon.auth.vo.OpenRegisterAccount;
import com.carbon.auth.vo.SecurityData;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import jakarta.annotation.Resource;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * 登录服务实现类
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private LoginMapper loginMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private SmsService smsService;
    @Autowired(required = false)
    private SystemServiceApi systemServiceApi;
    @Autowired(required = false)
    private RocketMQTemplate mqTemplate;

    @Override
    public LoginInfoVo byLoginName(LoginParam loginParam) {

        log.info("loginParam = " + loginParam);
        String accountName = StrUtil.trimToEmpty(loginParam.getAccountName());
        String password = loginParam.getPassword();

        SysAccount account = loginMapper.selectOne(Wrappers.lambdaQuery(SysAccount.class).eq(SysAccount::getAccountName, accountName));
        if (account == null) {
            throw new CommonBizException(ExpCodeEnum.SYSTEM_SECURITY_USER_NULL);
        }
        if (StrUtil.isNotEmpty(password) && !account.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
            throw new CommonBizException(ExpCodeEnum.SYSTEM_SECURITY_USER_PASSWORD_ERROR);
        }
        if (AccountConstant.ACCOUNT_STATUS_DISABLE.equals(account.getAccountStatus())) {
            throw new CommonBizException(ExpCodeEnum.SYS_ACCOUNT_DISABLE);
        }
        if (AccountConstant.ACCOUNT_STATUS_NO_OPENED.equals(account.getAccountStatus())) {
            throw new CommonBizException(ExpCodeEnum.SYS_ACCOUNT_NO_OPENED);
        }

        LoginInfoVo loginInfo;

        // 判断是否登录,如果已经登录直接返回
        loginInfo = getLoginInfoByRedis(account.getId());
        if (null != loginInfo) {
            return loginInfo;
        }

        // 创建登录信息,并且保存到redis
        String token = JwtUtil.generateToken(account.getId(), account.getTenantId());
        loginInfo = new LoginInfoVo();
        loginInfo.setToken(token);
        loginInfo.setSecurityData(getSecurityData(account.getId()));
        saveLoginInfoByRedis(loginInfo);

        return loginInfo;
    }

    @Override
    public void logout(Long accountId) {
        removeLoginInfoByRedis(accountId);
    }

    @Override
    public void register(RegisterParam param) {
        // 校验短信验证码
        smsService.checkValidateCode(param.getPhone(), param.getCode(), RedisKeyName.SMS_REGISTER_KEY);
        
        // 判断两次密码是否一致
        if (!param.getPassword().equals(param.getConfirmPassword())) {
            throw new CommonBizException(ExpCodeEnum.PASSWORD_NOT_SAME);
        }
        
        // 调用系统服务创建账号（如果系统服务可用）
        if (systemServiceApi != null) {
            SysAccountParam accountParam = new SysAccountParam();
            accountParam.setAccountName(param.getAccountName());
            accountParam.setPassword(param.getPassword());
            accountParam.setPhone(param.getPhone());
            accountParam.setEmail(param.getEmail());
            accountParam.setAccountStatus(AccountConstant.ACCOUNT_STATUS_ENABLE); // 注册后直接启用，允许登录
            accountParam.setAccountType("0380000001");//试用账户
            accountParam.setProductVersion("0400000001");//试用版
            accountParam.setRoleIds(CollUtil.newArrayList(9L));//角色ID
            
            try {
                ApiResult<Boolean> result = systemServiceApi.addSysAccount(accountParam);
                log.info("register:{}", JSONUtil.toJsonPrettyStr(result));
                
                // 检查 result 是否为 null
                if (result == null) {
                    log.error("系统服务返回结果为空");
                    throw new CommonBizException("注册失败，请稍后重试");
                }
                
                // 检查返回码，使用 equals 进行安全比较
                Integer resultCode = result.getCode();
                if (resultCode == null || !ApiCode.SUCCESS.getCode().equals(resultCode)) {
                    String errorMsg = result.getMsg() != null ? result.getMsg() : "注册失败";
                    log.error("系统服务返回失败，code: {}, msg: {}", resultCode, errorMsg);
                    throw new CommonBizException(errorMsg);
                }
                
                log.info("系统服务创建账号成功");
            } catch (CommonBizException e) {
                // 业务异常直接抛出
                throw e;
            } catch (Exception e) {
                log.error("调用系统服务创建账号失败: {}", e.getMessage(), e);
                throw new CommonBizException("注册失败，请稍后重试");
            }
        } else {
            log.warn("系统服务未配置，跳过创建账号");
            // 在实际环境中，这里应该直接在本地数据库创建账号
            throw new CommonBizException("系统服务未配置，无法完成注册");
        }
        
        //发送MQ消息（如果RocketMQ可用）
        if (mqTemplate != null) {
            try {
                OpenRegisterAccount registerAccount = BeanUtil.fillBean(new OpenRegisterAccount(), new ValueProvider<String>() {
                    @Override
                    public Object value(String s, Type type) {
                        return "";
                    }

                    @Override
                    public boolean containsKey(String s) {
                        return true;
                    }
                }, CopyOptions.create());//创建空对象，null转""

                registerAccount.setAccountName(param.getAccountName());
                registerAccount.setProductVersion("试用版");
                registerAccount.setContactNumber(param.getPhone());
                registerAccount.setAccountState("未开户");

                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                registerAccount.setRegistrationTime(dtf.format(now));

                Message<OpenRegisterAccount> msg = MessageBuilder
                        .withPayload(BeanUtil.copyProperties(registerAccount, OpenRegisterAccount.class)).build();
                mqTemplate.syncSend(RocketMqName.OpenRegisterAccount_MSG, msg, 3000, RocketDelayLevelConstant.SECOND10);
                log.info("注册MQ消息发送成功");
            } catch (Exception e) {
                log.warn("发送注册MQ消息失败，但不影响注册流程: {}", e.getMessage());
            }
        } else {
            log.warn("RocketMQ未配置，跳过发送注册消息");
        }
    }

    @Override
    public void forgotPassword(ForgotPasswordParam param) {
        //校验短信验证码
        smsService.checkValidateCode(param.getPhone(), param.getCode(), RedisKeyName.SMS_FORGOT_PASSWORD_KEY);
        
        // 判断两次密码是否一致
        if (!param.getPassword().equals(param.getConfirmPassword())) {
            throw new CommonBizException(ExpCodeEnum.PASSWORD_NOT_SAME);
        }
        
        //根据手机号查询用户
        SysAccount account = loginMapper.selectOne(Wrappers.lambdaQuery(SysAccount.class).eq(SysAccount::getPhone, param.getPhone()));
        if (account == null) {
            throw new CommonBizException(ExpCodeEnum.SYSTEM_SECURITY_USER_NULL);
        }
        
        //更新密码（如果系统服务可用）
        if (systemServiceApi != null) {
            try {
                ChangePasswordParam changePasswordParam = new ChangePasswordParam();
                changePasswordParam.setId(account.getId());
                // 忘记密码场景不需要验证旧密码，所以不设置 oldPasswordMd5
                changePasswordParam.setOldPasswordMd5(null);
                changePasswordParam.setNewPassword(param.getPassword());
                
                log.info("调用系统服务更新密码: accountId={}", account.getId());
                ApiResult result = systemServiceApi.updatePassword(changePasswordParam);
                log.info("系统服务返回结果: {}", JSONUtil.toJsonPrettyStr(result));
                
                // 检查 result 是否为 null
                if (result == null) {
                    log.error("系统服务返回结果为空");
                    throw new CommonBizException("密码重置失败，请稍后重试");
                }
                
                // 检查返回码，使用 equals 进行安全比较
                Integer resultCode = result.getCode();
                if (resultCode == null || !ApiCode.SUCCESS.getCode().equals(resultCode)) {
                    String errorMsg = result.getMsg() != null ? result.getMsg() : "密码重置失败";
                    log.error("系统服务返回失败，code: {}, msg: {}", resultCode, errorMsg);
                    throw new CommonBizException(errorMsg);
                }
                
                log.info("密码重置成功: accountId={}", account.getId());
            } catch (CommonBizException e) {
                // 业务异常直接抛出
                log.warn("密码重置业务异常: {}", e.getMessage());
                throw e;
            } catch (Exception e) {
                log.error("调用系统服务更新密码失败: {}, 异常类型: {}", e.getMessage(), e.getClass().getName(), e);
                throw new CommonBizException("密码重置失败，请稍后重试");
            }
        } else {
            log.warn("系统服务未配置，跳过更新密码");
            // 在实际环境中，这里应该直接在本地数据库更新密码
            throw new CommonBizException("系统服务未配置，无法完成密码重置");
        }
    }

    @Override
    public Boolean isLogin(Long accountId) {
        //判断 redisToken 是否失效,判断请求token是否和redis一致
        String requestToken = HttpContextUtils.getRequestToken();

        LoginInfoVo loginInfo = getLoginInfoByRedis(accountId);
        if (null == loginInfo) {
            return false;
        }

        String redisToken = loginInfo.getToken();
        return !StrUtil.isEmpty(redisToken) && redisToken.equals(requestToken);
    }

    @Override
    public Boolean verify(String accountName) {
        return loginMapper.selectOne(new QueryWrapper<SysAccount>().eq("account_name", accountName)) == null;
    }

    @Override
    public ApiResult<Boolean> checkPermission(String checkUrl) {
        log.info("checkPermission-url:{}", checkUrl);
        //获取登录信息
        LoginInfoVo loginInfo = getLoginInfoByRedis(HttpContextUtils.getAccountId());
        if (loginInfo == null) {
            return ApiResult.fail(ApiCode.UNAUTHORIZED);
        }
        return ApiResult.ok();
    }

    @Override
    public SecurityData getSecurityData(Long accountId) {
        SecurityData data = loginMapper.getSecurityDataAccountId(accountId);
        if (data == null) {
            return null;
        }
        if (StrUtil.isNotEmpty(data.getRoleCodeStr())) {
            data.setRoleCodes(Arrays.asList(data.getRoleCodeStr().split(",")));
        }
        if (StrUtil.isNotEmpty(data.getPermissionCodesStr())) {
            data.setPermissionCodes(Arrays.asList(data.getPermissionCodesStr().split(",")));
        }
        return data;
    }

    /**
     * redis 获取登录信息
     * 如果 redis 中的token不合法则返回空，用户未登录
     */
    private LoginInfoVo getLoginInfoByRedis(Long accountId) {
        String json = redisService.get(String.format(CommonRedisKey.LOGIN_USER, accountId));
        if (StrUtil.isEmpty(json)) {
            return null;
        }
        LoginInfoVo loginInfo = new Gson().fromJson(json, LoginInfoVo.class);
        //校验redis中的token
        if (!JwtUtil.isValidToken(loginInfo.getToken())) {
            return null;
        }
        return loginInfo;
    }

    /**
     * redis 存储登录信息
     */
    private void saveLoginInfoByRedis(LoginInfoVo loginInfoVo) {
        Long accountId = loginInfoVo.getSecurityData().getAccountId();
        redisService.setEx(String.format(CommonRedisKey.LOGIN_USER, accountId),
                JSONUtil.toJsonStr(loginInfoVo), JwtUtil.EXPIRE_SECOND, TimeUnit.SECONDS);
    }

    /**
     * 清除登录信息
     */
    private void removeLoginInfoByRedis(Long accountId) {
        redisService.remove(String.format(CommonRedisKey.LOGIN_USER, accountId));
    }

}

