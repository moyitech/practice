package com.carbon.system.service;

import com.carbon.system.entity.SysAccount;
import com.carbon.system.param.ChangePasswordParam;
import com.carbon.system.param.SysAccountParam;

/**
 * 系统账户服务接口
 */
public interface SysAccountService extends BaseService<SysAccount> {

    /**
     * 添加系统账号
     *
     * @param param 账号参数
     * @return 是否成功
     */
    Boolean addSysAccount(SysAccountParam param);

    /**
     * 修改密码
     *
     * @param param 修改密码参数
     * @return 是否成功
     */
    Boolean updatePassword(ChangePasswordParam param);
}


