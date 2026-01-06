package com.carbon.assets.service.feign;

import com.carbon.assets.common.ApiResult;
import com.carbon.assets.vo.LoginInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * 系统服务Feign客户端
 */
@FeignClient(name = "carbon-system", path = "/system")
public interface SystemServiceApi {

    /**
     * 获取登录信息（示例接口，实际需要根据系统服务定义）
     */
    @GetMapping("/login/info")
    ApiResult<LoginInfoVo> getLoginInfo(@RequestHeader("accountId") Long accountId);

}

