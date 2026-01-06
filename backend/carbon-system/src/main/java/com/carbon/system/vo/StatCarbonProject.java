package com.carbon.system.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 碳项目统计信息
 */
@Data
public class StatCarbonProject implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 减排量
     */
    private String reduction;

    /**
     * 开发状态
     */
    private String developmentState;

    /**
     * 交易状态
     */
    private String tradeState;

    /**
     * 碳估值
     */
    private BigDecimal carbonValuation = BigDecimal.ZERO;
}


