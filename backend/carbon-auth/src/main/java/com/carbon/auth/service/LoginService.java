package com.carbon.auth.service;

import com.carbon.auth.common.ApiResult;
import com.carbon.auth.param.ForgotPasswordParam;
import com.carbon.auth.param.LoginParam;
import com.carbon.auth.param.RegisterParam;
import com.carbon.auth.vo.LoginInfoVo;
import com.carbon.auth.vo.SecurityData;

/**
 * 登录服务接口
 */
public interface LoginService {

    /**
     * 用户名密码登录
     *
     * @param loginParam 登录参数
     * @return LoginInfoVo
     */
    LoginInfoVo byLoginName(LoginParam loginParam);

    /**
     * 退出登录
     *
     * @param accountId 账户ID
     */
    void logout(Long accountId);

    /**
     * 注册
     *
     * @param param RegisterParam
     */
    void register(RegisterParam param);

    /**
     * 忘记密码
     *
     * @param param 参数
     */
    void forgotPassword(ForgotPasswordParam param);

    /**
     * 获取认证数据
     *
     * @param accountId 账户ID
     * @return SecurityData
     */
    SecurityData getSecurityData(Long accountId);

    /**
     * 根据请求头中的token判断是否登录
     *
     * @param accountId 账户ID
     * @return Boolean
     */
    Boolean isLogin(Long accountId);

    /**
     * 校验权限
     *
     * @param checkUrl 需要校验的url
     * @return ApiResult
     */
    ApiResult<Boolean> checkPermission(String checkUrl);

    /**
     * 校验账户
     *
     * @param accountName 账户名
     * @return Boolean
     */
    Boolean verify(String accountName);

}

