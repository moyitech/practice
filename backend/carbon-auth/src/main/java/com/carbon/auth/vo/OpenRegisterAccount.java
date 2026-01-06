package com.carbon.auth.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 开户注册账号VO
 */
@Data
public class OpenRegisterAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    private String accountName;

    private String productVersion;

    private String contactNumber;

    private String accountState;

    private String registrationTime;

}

