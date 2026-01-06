package com.carbon.assets.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 碳元注册表实体类
 */
@Data
@TableName("carbon_metaregistry")
public class CarbonMetaregistry implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String projectName;

    private String methodologyCode;

    private String methodologyName;

    private String certifiedStandard;

    private String projectVerifier;

    private String projectOwner;

    private String projectScopeTypeCode;

    private Date issuingDate;

    private String projectStatusCode;

}

