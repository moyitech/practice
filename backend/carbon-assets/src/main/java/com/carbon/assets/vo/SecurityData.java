package com.carbon.assets.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 安全数据VO
 */
@Data
public class SecurityData implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long accountId;

    private String accountName;

    private String phone;

    private String avatar;

    private Long tenantId;

    private String tenantName;

}

