package com.carbon.auth;

import com.carbon.auth.param.LoginParam;
import com.carbon.auth.service.LoginService;
import com.carbon.auth.vo.LoginInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * LoginService单元测试
 */
@Slf4j
@SpringBootTest
public class LoginServiceTest {

    @Autowired
    private LoginService loginService;

    /**
     * 测试用户名密码登录
     */
    @Test
    public void testByLoginName() {
        LoginParam loginParam = new LoginParam();
        loginParam.setAccountName("admin");
        loginParam.setPassword("123456");
        
        try {
            LoginInfoVo loginInfo = loginService.byLoginName(loginParam);
            
            log.info("=== 登录成功 ===");
            log.info("Token: {}", loginInfo.getToken());
            log.info("账户ID: {}", loginInfo.getSecurityData().getAccountId());
            log.info("账户名: {}", loginInfo.getSecurityData().getAccountName());
            log.info("手机号: {}", loginInfo.getSecurityData().getPhone());
            log.info("租户ID: {}", loginInfo.getSecurityData().getTenantId());
        } catch (Exception e) {
            log.error("登录失败: {}", e.getMessage());
        }
    }

    /**
     * 测试判断是否登录
     */
    @Test
    public void testIsLogin() {
        Long accountId = 1L;
        
        Boolean isLogin = loginService.isLogin(accountId);
        
        log.info("账户ID {} 的登录状态: {}", accountId, isLogin ? "已登录" : "未登录");
    }

    /**
     * 测试退出登录
     */
    @Test
    public void testLogout() {
        Long accountId = 1L;
        
        try {
            loginService.logout(accountId);
            log.info("账户ID {} 退出登录成功", accountId);
        } catch (Exception e) {
            log.error("退出登录失败: {}", e.getMessage());
        }
    }

}

