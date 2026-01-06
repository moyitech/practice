package com.carbon.auth.param;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 注册参数
 */
@Data
public class RegisterParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "账户名不能为空")
    private String accountName;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;

    @NotBlank(message = "手机号不能为空")
    private String phone;

    private String email;

    @NotBlank(message = "验证码不能为空")
    private String code;

}

