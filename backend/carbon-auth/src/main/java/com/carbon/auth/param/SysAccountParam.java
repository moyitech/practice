package com.carbon.auth.param;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 系统账号参数
 */
@Data
public class SysAccountParam implements Serializable {

    private static final long serialVersionUID = 1L;

    private String accountName;

    private String password;

    private String phone;

    private String email;

    private String accountStatus;

    private String accountType;

    private String productVersion;

    private List<Long> roleIds;

}

