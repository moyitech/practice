package com.carbon.system.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 系统账户角色VO
 */
@Data
public class SysAccountRoleVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long accountId;

    private List<Long> roleIds;

    private List<String> roleNames;
}










