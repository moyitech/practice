package com.carbon.assets.param;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 碳信用资产添加参数
 */
@Data
@Schema(description = "碳信用资产添加参数")
public class CarbonCreditAssetsAddParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "项目ID")
    @NotNull(message = "项目ID不能为空")
    private Long carbonProjectId;

    @Schema(description = "交易所ID")
    private Long carbonExchangeId;

    @Schema(description = "核证机构")
    private String certifiedAgency;

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

    @Schema(description = "交易总价")
    private BigDecimal buyTotalPrice;

    @Schema(description = "交易单价")
    private BigDecimal buyUnitPrice;

    @Schema(description = "交易日期")
    private Date buyDate;

    @Schema(description = "交易凭证")
    private String buyCertificate;

    @Schema(description = "交易凭证文件名")
    private String buyCertificateFileName;

    @Schema(description = "持仓总量")
    @NotNull(message = "持仓总量不能为空")
    private BigDecimal total;

    @Schema(description = "可用数量")
    private BigDecimal availableAmount;

    @Schema(description = "冻结数量")
    private BigDecimal frozenAmount;

    @Schema(description = "锁定数量")
    private BigDecimal lockedAmount;

    @Schema(description = "资产估值")
    private BigDecimal valuation;

}

