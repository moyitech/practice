package com.carbon.system.service.impl;

import com.carbon.system.entity.SysAccount;
import com.carbon.system.mapper.SysRoleMapper;
import com.carbon.system.service.BaseServiceImpl;
import com.carbon.system.service.SysRoleService;
import com.carbon.system.vo.SysAccountRoleVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * 系统角色服务实现类
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleMapper, SysAccount> implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Override
    public SysAccountRoleVo getAccountRole(Long accountId) {
        return sysRoleMapper.getAccountRole(accountId);
    }
}









