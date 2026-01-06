package com.carbon.assets.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 碳配额资产查询参数
 */
@Data
@Schema(description = "碳配额资产查询参数")
public class CarbonQuotaAssetsQueryParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "当前页")
    private Long current = 1L;

    @Schema(description = "每页大小")
    private Long size = 10L;

    @Schema(description = "是否升序")
    private Boolean asc = false;

    @Schema(description = "排序字段")
    private String sortField;

    @Schema(description = "机构名称")
    private String agencyName;

    @Schema(description = "资产状态")
    private String assetsStatus;

    @Schema(description = "交易状态")
    private String transactionStatus;

    @Schema(description = "有效期开始")
    private Date expiryDateStart;

    @Schema(description = "有效期结束")
    private Date expiryDateEnd;

}

