package com.carbon.auth.util;

import cn.hutool.core.util.StrUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

/**
 * HTTP上下文工具类
 */
public class HttpContextUtils {

    /**
     * Token请求头名称
     */
    private static final String TOKEN_HEADER = "Authorization";

    /**
     * 获取当前请求
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return null;
        }
        return attributes.getRequest();
    }

    /**
     * 获取请求头中的Token
     *
     * @return Token
     */
    public static String getRequestToken() {
        HttpServletRequest request = getRequest();
        if (request == null) {
            return null;
        }
        String token = request.getHeader(TOKEN_HEADER);
        if (StrUtil.isEmpty(token)) {
            return null;
        }
        // 如果token以"Bearer "开头，去掉前缀
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return token;
    }

    /**
     * 获取当前登录用户的账户ID
     *
     * @return 账户ID
     */
    public static Long getAccountId() {
        String token = getRequestToken();
        if (StrUtil.isEmpty(token)) {
            return null;
        }
        return JwtUtil.getAccountId(token);
    }

}

