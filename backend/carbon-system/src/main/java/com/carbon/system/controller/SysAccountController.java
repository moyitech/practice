package com.carbon.system.controller;

import com.carbon.system.common.ApiResult;
import com.carbon.system.common.BaseController;
import com.carbon.system.param.ChangePasswordParam;
import com.carbon.system.param.SysAccountParam;
import com.carbon.system.service.SysAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统账户控制器
 */
@Slf4j
@RestController
@RequestMapping("/system/account")
public class SysAccountController extends BaseController {

    @Autowired
    private SysAccountService sysAccountService;

    /**
     * 添加系统账号
     *
     * @param param 账号参数
     * @return 结果
     */
    @PostMapping("/add")
    public ApiResult<Boolean> addSysAccount(@RequestBody SysAccountParam param) {
        log.info("添加系统账号: {}", param.getAccountName());
        try {
            Boolean result = sysAccountService.addSysAccount(param);
            return ApiResult.ok(result);
        } catch (Exception e) {
            log.error("添加系统账号失败: {}", e.getMessage(), e);
            return ApiResult.fail(e.getMessage());
        }
    }

    /**
     * 修改密码
     *
     * @param param 修改密码参数
     * @return 结果
     */
    @PostMapping("/updatePassword")
    public ApiResult<Boolean> updatePassword(@RequestBody ChangePasswordParam param) {
        log.info("修改密码: accountId={}", param.getId());
        try {
            Boolean result = sysAccountService.updatePassword(param);
            return ApiResult.ok(result);
        } catch (Exception e) {
            log.error("修改密码失败: {}", e.getMessage(), e);
            return ApiResult.fail(e.getMessage());
        }
    }
}

