package com.carbon.auth.param;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 登录参数
 */
@Data
public class LoginParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "账户名不能为空")
    private String accountName;

    @NotBlank(message = "密码不能为空")
    private String password;

}

