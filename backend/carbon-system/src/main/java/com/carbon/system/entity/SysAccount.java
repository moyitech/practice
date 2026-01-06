package com.carbon.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统账户实体类
 */
@Data
@TableName("sys_account")
public class SysAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String accountName;

    private String username;

    private String password;

    private String phone;

    private String avatar;

    private String email;

    private String productVersion;

    private String token;

    private String accountType;

    private String accountStatus;

    private Long tenantId;

    private String remarks;

    private Long creatorId;

    private Long updatedId;

    private Date createdTime;

    private Date updatedTime;

    private Date validityPeriod;
}


