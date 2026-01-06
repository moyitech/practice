package com.carbon.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.carbon.system.entity.SysAccount;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 系统账户Mapper接口
 */
@Repository
public interface SysAccountMapper extends BaseMapper<SysAccount> {

    /**
     * 插入账号角色关联关系
     *
     * @param accountId 账号ID
     * @param roleId    角色ID
     */
    @Insert("INSERT INTO sys_account_role (account_id, role_id) VALUES (#{accountId}, #{roleId})")
    void insertAccountRole(@Param("accountId") Long accountId, @Param("roleId") Long roleId);
}


