package com.carbon.system.service;

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
        return 1L; // 默认返回1（测试用）
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
        return 1L; // 默认返回1（测试用）
    }
}





