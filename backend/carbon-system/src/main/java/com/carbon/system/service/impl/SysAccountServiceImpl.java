package com.carbon.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.carbon.system.entity.SysAccount;
import com.carbon.system.mapper.SysAccountMapper;
import com.carbon.system.mapper.SysRoleMapper;
import com.carbon.system.param.ChangePasswordParam;
import com.carbon.system.param.SysAccountParam;
import com.carbon.system.service.BaseServiceImpl;
import com.carbon.system.service.SysAccountService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;

/**
 * 系统账户服务实现类
 */
@Slf4j
@Service
public class SysAccountServiceImpl extends BaseServiceImpl<SysAccountMapper, SysAccount> implements SysAccountService {

    @Resource
    private SysAccountMapper sysAccountMapper;

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addSysAccount(SysAccountParam param) {
        // 检查账号名是否已存在
        LambdaQueryWrapper<SysAccount> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysAccount::getAccountName, param.getAccountName());
        SysAccount existAccount = sysAccountMapper.selectOne(queryWrapper);
        if (existAccount != null) {
            throw new RuntimeException("账号名已存在");
        }

        // 检查手机号是否已存在
        if (StrUtil.isNotEmpty(param.getPhone())) {
            LambdaQueryWrapper<SysAccount> phoneWrapper = new LambdaQueryWrapper<>();
            phoneWrapper.eq(SysAccount::getPhone, param.getPhone());
            SysAccount existPhoneAccount = sysAccountMapper.selectOne(phoneWrapper);
            if (existPhoneAccount != null) {
                throw new RuntimeException("手机号已被注册");
            }
        }

        // 创建账号
        SysAccount account = new SysAccount();
        account.setAccountName(param.getAccountName());
        account.setPassword(DigestUtils.md5DigestAsHex(param.getPassword().getBytes()));
        account.setPhone(param.getPhone());
        account.setEmail(param.getEmail());
        account.setAccountStatus(param.getAccountStatus());
        account.setAccountType(param.getAccountType());
        account.setProductVersion(param.getProductVersion());
        account.setTenantId(1L); // 默认租户ID
        account.setCreatedTime(new Date());
        account.setUpdatedTime(new Date());

        int result = sysAccountMapper.insert(account);
        if (result <= 0) {
            throw new RuntimeException("创建账号失败");
        }

        // 关联角色（如果提供了角色ID）
        if (CollUtil.isNotEmpty(param.getRoleIds())) {
            for (Long roleId : param.getRoleIds()) {
                // 插入账号角色关联关系
                sysAccountMapper.insertAccountRole(account.getId(), roleId);
            }
        }

        log.info("成功创建账号: {}, ID: {}", param.getAccountName(), account.getId());
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updatePassword(ChangePasswordParam param) {
        SysAccount account = sysAccountMapper.selectById(param.getId());
        if (account == null) {
            throw new RuntimeException("账号不存在");
        }

        // 验证旧密码
        if (StrUtil.isNotEmpty(param.getOldPasswordMd5()) 
                && !account.getPassword().equals(param.getOldPasswordMd5())) {
            throw new RuntimeException("旧密码不正确");
        }

        // 更新密码
        account.setPassword(DigestUtils.md5DigestAsHex(param.getNewPassword().getBytes()));
        account.setUpdatedTime(new Date());

        int result = sysAccountMapper.updateById(account);
        if (result <= 0) {
            throw new RuntimeException("更新密码失败");
        }

        log.info("成功更新账号密码: ID: {}", param.getId());
        return true;
    }
}


