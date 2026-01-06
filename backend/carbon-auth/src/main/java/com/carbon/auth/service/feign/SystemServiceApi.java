package com.carbon.auth.service.feign;

import com.carbon.auth.common.ApiResult;
import com.carbon.auth.param.ChangePasswordParam;
import com.carbon.auth.param.SysAccountParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 系统服务Feign客户端
 */
@FeignClient(name = "carbon-system", path = "/system")
public interface SystemServiceApi {

    /**
     * 添加系统账号
     *
     * @param param 账号参数
     * @return 结果
     */
    @PostMapping("/account/add")
    ApiResult<Boolean> addSysAccount(@RequestBody SysAccountParam param);

    /**
     * 修改密码
     *
     * @param param 修改密码参数
     * @return 结果
     */
    @PostMapping("/account/updatePassword")
    ApiResult<Boolean> updatePassword(@RequestBody ChangePasswordParam param);

}

