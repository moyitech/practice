package com.carbon.auth;

import com.carbon.auth.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * JwtUtil单元测试
 */
@Slf4j
public class JwtUtilTest {

    /**
     * 测试生成Token
     */
    @Test
    public void testGenerateToken() {
        Long accountId = 1L;
        Long tenantId = 1L;
        
        String token = JwtUtil.generateToken(accountId, tenantId);
        
        log.info("=== 生成Token成功 ===");
        log.info("Token: {}", token);
    }

    /**
     * 测试解析Token
     */
    @Test
    public void testParseToken() {
        Long accountId = 1L;
        Long tenantId = 1L;
        
        // 生成Token
        String token = JwtUtil.generateToken(accountId, tenantId);
        log.info("生成的Token: {}", token);
        
        // 解析Token
        Claims claims = JwtUtil.parseToken(token);
        
        if (claims != null) {
            log.info("=== 解析Token成功 ===");
            log.info("账户ID: {}", claims.get("accountId"));
            log.info("租户ID: {}", claims.get("tenantId"));
            log.info("签发时间: {}", claims.getIssuedAt());
            log.info("过期时间: {}", claims.getExpiration());
        } else {
            log.error("Token解析失败");
        }
    }

    /**
     * 测试验证Token是否有效
     */
    @Test
    public void testIsValidToken() {
        Long accountId = 1L;
        Long tenantId = 1L;
        
        String token = JwtUtil.generateToken(accountId, tenantId);
        
        boolean isValid = JwtUtil.isValidToken(token);
        
        log.info("Token有效性: {}", isValid ? "有效" : "无效");
    }

    /**
     * 测试从Token中获取账户ID
     */
    @Test
    public void testGetAccountId() {
        Long accountId = 123L;
        Long tenantId = 1L;
        
        String token = JwtUtil.generateToken(accountId, tenantId);
        
        Long extractedAccountId = JwtUtil.getAccountId(token);
        
        log.info("原始账户ID: {}", accountId);
        log.info("提取的账户ID: {}", extractedAccountId);
        log.info("账户ID匹配: {}", accountId.equals(extractedAccountId));
    }

}

