package com.carbon.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 碳月度统计VO
 */
@Data
public class StatCarbonMonthVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 统计日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date statDate;

    /**
     * 碳信用
     */
    private BigDecimal carbonCredit = BigDecimal.ZERO;

    /**
     * 碳配额
     */
    private BigDecimal carbonQuota = BigDecimal.ZERO;

    /**
     * 绿色积分
     */
    private BigDecimal greenScore = BigDecimal.ZERO;

    /**
     * 碳供应量
     */
    private BigDecimal carbonSupply = BigDecimal.ZERO;

    /**
     * 碳供应总量
     */
    private BigDecimal carbonSupplyTotal = BigDecimal.ZERO;

    /**
     * 碳估值
     */
    private BigDecimal carbonValuation = BigDecimal.ZERO;
}


