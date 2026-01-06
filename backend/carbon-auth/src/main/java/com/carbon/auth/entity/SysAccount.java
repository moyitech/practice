package com.carbon.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * SysAccount对象 - 系统账号实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysAccount extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String accountName;

    private String phone;

    private String email;

    private String password;

    private String avatar;

    private String accountStatus;

    private Long tenantId;

    private String remarks;

}

