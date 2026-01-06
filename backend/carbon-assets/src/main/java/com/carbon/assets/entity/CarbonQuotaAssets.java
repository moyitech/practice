package com.carbon.assets.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 碳配额资产实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("carbon_quota_assets")
@Schema(description = "碳配额资产")
public class CarbonQuotaAssets extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "持有机构名称")
    private String agencyName;

    @Schema(description = "交易所ID")
    private Long carbonExchangeId;

    @Schema(description = "签发机构")
    private String issuingAgency;

    @Schema(description = "签发凭证")
    private String issuingCertificates;

    @Schema(description = "签发凭证文件名")
    private String issuingCertificatesFileName;

    @Schema(description = "签发日期")
    private Date issuingDate;

    @Schema(description = "有效期")
    private Date expiryDate;

    @Schema(description = "资产状态")
    private String assetsStatus;

    @Schema(description = "交易状态")
    private String transactionStatus;

    @Schema(description = "购入总价")
    private BigDecimal buyTotalPrice;

    @Schema(description = "购入单价")
    private BigDecimal buyUnitPrice;

    @Schema(description = "购入日期")
    private Date buyDate;

    @Schema(description = "购入凭证")
    private String buyCertificate;

    @Schema(description = "购入凭证文件名")
    private String buyCertificateFileName;

    @Schema(description = "持仓总量")
    private BigDecimal total;

    @Schema(description = "可用数量")
    private BigDecimal availableAmount;

    @Schema(description = "冻结数量")
    private BigDecimal frozenAmount;

    @Schema(description = "锁定数量")
    private BigDecimal lockedAmount;

    @Schema(description = "资产估值")
    private BigDecimal valuation;

    @Schema(description = "租户ID")
    private Long tenantId;

}

