package com.carbon.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 账户统计信息VO
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatAccountVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 账户名称
     */
    private String accountName;

    /**
     * 企业名称
     */
    private String enterpriseName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 账户类型
     */
    private String accountType;

    /**
     * 产品版本
     */
    private String productVersion;

    /**
     * 账户有效期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date accountValidity;

    /**
     * 是否过期
     */
    private Boolean expired;

    /**
     * 角色名称列表
     */
    private List<String> roleNames;

    /**
     * ESG评分
     */
    private Integer esgScore;

    /**
     * 碳中和比率
     */
    private Integer carbonNeutralRatio;
}

