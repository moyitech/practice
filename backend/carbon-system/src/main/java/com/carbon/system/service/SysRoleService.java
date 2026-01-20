package com.carbon.system.service;

import com.carbon.system.entity.SysAccount;
import com.carbon.system.vo.SysAccountRoleVo;

/**
 * 系统角色服务接口
 */
public interface SysRoleService extends BaseService<SysAccount> {

    /**
     * 获取账户角色信息
     * @param accountId 账户ID
     * @return 账户角色VO
     */
    SysAccountRoleVo getAccountRole(Long accountId);
}










