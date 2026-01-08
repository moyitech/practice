package com.carbon.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.carbon.system.entity.SysAccount;
import com.carbon.system.vo.SysAccountRoleVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 系统角色Mapper接口
 */
@Repository
public interface SysRoleMapper extends BaseMapper<SysAccount> {

    /**
     * 获取账户角色信息
     * @param accountId 账户ID
     * @return 账户角色VO
     */
    SysAccountRoleVo getAccountRole(@Param("accountId") Long accountId);
}





