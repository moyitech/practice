package com.carbon.assets.common;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 控制器基类
 */
public class BaseController {

    /**
     * 获取当前请求
     */
    protected HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取当前账户ID（从请求头或session中获取）
     */
    protected Long getCurrentAccountId() {
        String accountId = getRequest().getHeader("accountId");
        if (accountId != null) {
            return Long.parseLong(accountId);
        }
        return 1L;
    }

    /**
     * 获取当前租户ID（从请求头或session中获取）
     */
    protected Long getCurrentTenantId() {
        String tenantId = getRequest().getHeader("tenantId");
        if (tenantId != null) {
            return Long.parseLong(tenantId);
        }
        return 1L;
    }
}

