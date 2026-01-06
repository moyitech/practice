package com.carbon.auth.controller;

import com.carbon.auth.common.ApiResult;
import com.carbon.auth.param.ForgotPasswordParam;
import com.carbon.auth.param.LoginParam;
import com.carbon.auth.param.RegisterParam;
import com.carbon.auth.service.LoginService;
import com.carbon.auth.service.SmsService;
import com.carbon.auth.util.HttpContextUtils;
import com.carbon.auth.vo.LoginInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 登录控制器
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private SmsService smsService;

    @PostMapping("/login")
    public ApiResult<LoginInfoVo> login(@Validated @RequestBody LoginParam loginParam, HttpServletRequest request) {
        return ApiResult.ok(loginService.byLoginName(loginParam));
    }

    @GetMapping("/logout")
    public ApiResult<Boolean> logout() {
        loginService.logout(HttpContextUtils.getAccountId());
        return ApiResult.ok();
    }

    @GetMapping("/register/code/{phone}")
    public ApiResult<Boolean> sendRegisterCode(@PathVariable String phone) {
        smsService.sendRegisterCode(phone);
        return ApiResult.ok("发送成功");
    }

    @PostMapping("/register")
    public ApiResult register(@Validated @RequestBody RegisterParam param) {
        loginService.register(param);
        return ApiResult.ok();
    }

    @GetMapping("/forgotPassword/code/{phone}")
    public ApiResult<Boolean> sendForgotPasswordCode(@PathVariable String phone) {
        smsService.sendForgotPasswordCode(phone);
        return ApiResult.ok("发送成功");
    }

    @PostMapping("/forgotPassword")
    public ApiResult forgotPassword(@Validated @RequestBody ForgotPasswordParam param) {
        loginService.forgotPassword(param);
        return ApiResult.ok();
    }

    @GetMapping("/verify/{accountName}")
    public ApiResult<Boolean> verifyAccountName(@PathVariable String accountName) {
        return ApiResult.ok(loginService.verify(accountName));
    }

}

