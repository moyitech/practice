package com.carbon.assets.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 碳元注册查询VO
 */
@Data
public class CarbonMetaregistryQueryVo implements Serializable {

    private static final long serialVersionUID = 1L;

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

