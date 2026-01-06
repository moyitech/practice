package com.carbon.auth;

import com.carbon.auth.mapper.LoginMapper;
import com.carbon.auth.vo.SecurityData;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * LoginMapper单元测试
 */
@Slf4j
@SpringBootTest
public class LoginMapperTest {

    @Autowired
    private LoginMapper loginMapper;

    /**
     * 测试根据账户ID获取安全数据
     */
    @Test
    public void testGetSecurityDataAccountId() {
        // 测试账户ID，请根据实际数据库中的数据修改
        Long accountId = 1L;
        
        SecurityData securityData = loginMapper.getSecurityDataAccountId(accountId);
        
        if (securityData != null) {
            log.info("=== 获取安全数据成功 ===");
            log.info("账户ID: {}", securityData.getAccountId());
            log.info("账户名: {}", securityData.getAccountName());
            log.info("手机号: {}", securityData.getPhone());
            log.info("租户ID: {}", securityData.getTenantId());
            log.info("租户名称: {}", securityData.getTenantName());
            log.info("角色编码: {}", securityData.getRoleCodeStr());
            log.info("权限编码: {}", securityData.getPermissionCodesStr());
        } else {
            log.warn("未找到账户ID为 {} 的安全数据", accountId);
        }
    }

}

