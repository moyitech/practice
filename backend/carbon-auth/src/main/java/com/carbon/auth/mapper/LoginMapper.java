package com.carbon.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.carbon.auth.entity.SysAccount;
import com.carbon.auth.vo.SecurityData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 登录Mapper接口
 */
@Repository
public interface LoginMapper extends BaseMapper<SysAccount> {

    /**
     * 根据账户ID获取安全数据
     *
     * @param accountId 账户ID
     * @return SecurityData
     */
    SecurityData getSecurityDataAccountId(@Param("accountId") Long accountId);

}

