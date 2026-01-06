package com.carbon.assets.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 碳配额资产查询VO
 */
@Data
public class CarbonQuotaAssetsQueryVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String agencyName;

    private Long carbonExchangeId;

    private String issuingAgency;

    private String issuingCertificates;

    private String issuingCertificatesFileName;

    private Date issuingDate;

    private Date expiryDate;

    private String assetsStatus;

    private String transactionStatus;

    private BigDecimal buyTotalPrice;

    private BigDecimal buyUnitPrice;

    private Date buyDate;

    private String buyCertificate;

    private String buyCertificateFileName;

    private BigDecimal total;

    private BigDecimal availableAmount;

    private BigDecimal frozenAmount;

    private BigDecimal lockedAmount;

    private BigDecimal valuation;

    private Long tenantId;

    private Long creatorId;

    private Long updatedId;

    private Date createdTime;

    private Date updatedTime;

    // 关联查询字段
    private String carbonExchangeName;

    private String carbonExchangeWebsite;

}

