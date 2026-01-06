package com.carbon.assets.param;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 碳配额资产参数（用于区块链服务）
 */
@Data
public class CarbonQuotaAssetsParam implements Serializable {

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

}

