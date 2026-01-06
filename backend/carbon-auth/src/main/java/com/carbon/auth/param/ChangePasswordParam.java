package com.carbon.auth.param;

import lombok.Data;

import java.io.Serializable;

/**
 * 修改密码参数
 */
@Data
public class ChangePasswordParam implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String oldPasswordMd5;

    private String newPassword;

}

