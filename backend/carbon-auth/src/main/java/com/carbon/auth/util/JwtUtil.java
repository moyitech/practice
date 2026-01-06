package com.carbon.auth.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 */
public class JwtUtil {

    /**
     * JWT密钥
     */
    private static final String SECRET = "carbon-auth-secret-key-2024";

    /**
     * 过期时间（秒） - 7天
     */
    public static final long EXPIRE_SECOND = 7 * 24 * 60 * 60;

    /**
     * 生成Token
     *
     * @param accountId 账户ID
     * @param tenantId  租户ID
     * @return Token
     */
    public static String generateToken(Long accountId, Long tenantId) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + EXPIRE_SECOND * 1000);

        Map<String, Object> claims = new HashMap<>();
        claims.put("accountId", accountId);
        claims.put("tenantId", tenantId);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    /**
     * 解析Token
     *
     * @param token Token
     * @return Claims
     */
    public static Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 验证Token是否有效
     *
     * @param token Token
     * @return 是否有效
     */
    public static boolean isValidToken(String token) {
        try {
            Claims claims = parseToken(token);
            if (claims == null) {
                return false;
            }
            Date expiration = claims.getExpiration();
            return expiration.after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 从Token中获取账户ID
     *
     * @param token Token
     * @return 账户ID
     */
    public static Long getAccountId(String token) {
        Claims claims = parseToken(token);
        if (claims == null) {
            return null;
        }
        Object accountId = claims.get("accountId");
        if (accountId instanceof Integer) {
            return ((Integer) accountId).longValue();
        }
        return (Long) accountId;
    }

}

