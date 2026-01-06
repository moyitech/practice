package com.carbon.auth.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

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

    private String contactsPhone;

    private String roleCodeStr;

    private String permissionCodesStr;

    private List<String> roleCodes;

    private List<String> permissionCodes;

}

