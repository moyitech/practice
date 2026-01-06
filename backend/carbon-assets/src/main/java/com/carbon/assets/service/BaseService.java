package com.carbon.assets.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Service基类接口
 */
public interface BaseService<T> extends IService<T> {

    /**
     * 获取当前请求
     */
    default HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes != null ? attributes.getRequest() : null;
    }

    /**
     * 获取当前账户ID
     */
    default Long getCurrentAccountId() {
        HttpServletRequest request = getRequest();
        if (request != null) {
            String accountId = request.getHeader("accountId");
            if (accountId != null) {
                return Long.parseLong(accountId);
            }
        }
        return 1L;
    }

    /**
     * 获取当前租户ID
     */
    default Long getCurrentTenantId() {
        HttpServletRequest request = getRequest();
        if (request != null) {
            String tenantId = request.getHeader("tenantId");
            if (tenantId != null) {
                return Long.parseLong(tenantId);
            }
        }
        return 1L;
    }

    /**
     * 获取登录信息
     */
    default com.carbon.assets.vo.LoginInfoVo getLoginInfoVo() {
        // 简化实现，实际应该从Redis或JWT中获取
        com.carbon.assets.vo.LoginInfoVo loginInfoVo = new com.carbon.assets.vo.LoginInfoVo();
        com.carbon.assets.vo.SecurityData securityData = new com.carbon.assets.vo.SecurityData();
        securityData.setAccountId(getCurrentAccountId());
        securityData.setTenantId(getCurrentTenantId());
        securityData.setAccountName("admin");
        securityData.setTenantName("测试租户");
        securityData.setPhone("13800138000");
        loginInfoVo.setSecurityData(securityData);
        return loginInfoVo;
    }

}

