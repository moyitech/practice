package com.carbon.assets.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 碳信用资产查询参数
 */
@Data
@Schema(description = "碳信用资产查询参数")
public class CarbonCreditAssetsQueryParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "当前页")
    private Long current = 1L;

    @Schema(description = "每页大小")
    private Long size = 10L;

    @Schema(description = "是否升序")
    private Boolean asc = false;

    @Schema(description = "项目名称")
    private String projectName;

    @Schema(description = "方法名称")
    private String methodName;

    @Schema(description = "认证标准")
    private String certificationCriteria;

    @Schema(description = "资产状态")
    private String assetsStatus;

    @Schema(description = "交易状态")
    private String transactionStatus;

    @Schema(description = "行业代码")
    private String industryCode;

    @Schema(description = "签发日期开始")
    private Date issuingDateStart;

    @Schema(description = "签发日期结束")
    private Date issuingDateEnd;

    @Schema(description = "项目范围代码")
    private String projectScopeCode;

}

