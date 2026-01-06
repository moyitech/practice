package com.carbon.auth.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录信息VO
 */
@Data
public class LoginInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String token;

    private SecurityData securityData;

}

